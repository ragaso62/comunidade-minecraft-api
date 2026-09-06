using System;
using System.Collections.Generic;
using System.Data;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GrimDashboard
{
    public partial class Form1 : Form
    {
        // Ajuste aqui se o backend estiver rodando em outra porta/host
        private const string BASE_URL = "http://localhost:8080/api";

        private static readonly HttpClient http = new HttpClient();

        public Form1()
        {
            InitializeComponent();
            ConfigurarComboRelatorios();
        }

        private void ConfigurarComboRelatorios()
        {
            cmbRelatorio.Items.Clear();
            cmbRelatorio.Items.Add("Jogadores mais ativos");
            cmbRelatorio.Items.Add("Tempo médio de sessão");
            cmbRelatorio.Items.Add("Violações por jogador");
            cmbRelatorio.Items.Add("Violações por tipo");
            cmbRelatorio.Items.Add("Linha do tempo de violações (usar campo de nickname)");
            cmbRelatorio.SelectedIndex = 0;
        }

        private async void btnCarregar_Click(object sender, EventArgs e)
        {
            btnCarregar.Enabled = false;
            try
            {
                string url = MontarUrl();
                if (url == null)
                {
                    MessageBox.Show("Digite um nickname no campo de texto para esse relatório.");
                    return;
                }

                string json = await http.GetStringAsync(url);
                var linhas = JsonSerializer.Deserialize<List<Dictionary<string, JsonElement>>>(json);
                PreencherGrid(linhas);
            }
            catch (HttpRequestException ex)
            {
                MessageBox.Show("Não foi possível conectar ao backend. Ele está rodando? Detalhe: " + ex.Message,
                    "Erro de conexão", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Erro inesperado: " + ex.Message, "Erro", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                btnCarregar.Enabled = true;
            }
        }

        /// <summary>
        /// Decide qual endpoint chamar de acordo com o item selecionado
        /// no ComboBox. Retorna null se faltar um dado obrigatório
        /// (ex.: nickname para a linha do tempo).
        /// </summary>
        private string MontarUrl()
        {
            switch (cmbRelatorio.SelectedIndex)
            {
                case 0: return $"{BASE_URL}/jogadores/mais-ativos";
                case 1: return $"{BASE_URL}/jogadores/tempo-medio-sessao";
                case 2: return $"{BASE_URL}/violacoes/por-jogador";
                case 3: return $"{BASE_URL}/violacoes/por-tipo";
                case 4:
                    string nick = txtNickname.Text.Trim();
                    if (string.IsNullOrEmpty(nick)) return null;
                    return $"{BASE_URL}/violacoes/linha-do-tempo/{Uri.EscapeDataString(nick)}";
                default: return $"{BASE_URL}/jogadores/mais-ativos";
            }
        }

        /// <summary>
        /// Converte a lista de linhas (cada uma um dicionário coluna->valor)
        /// numa DataTable e exibe no DataGridView. Como cada relatório tem
        /// colunas diferentes, montamos a tabela dinamicamente em vez de
        /// usar um modelo fixo.
        /// </summary>
        private void PreencherGrid(List<Dictionary<string, JsonElement>> linhas)
        {
            var tabela = new DataTable();

            if (linhas == null || linhas.Count == 0)
            {
                dgvResultados.DataSource = tabela;
                MessageBox.Show("Nenhum resultado encontrado para esse relatório.");
                return;
            }

            // Cria as colunas com base nas chaves da primeira linha
            foreach (var coluna in linhas[0].Keys)
            {
                tabela.Columns.Add(coluna);
            }

            foreach (var linha in linhas)
            {
                var novaLinha = tabela.NewRow();
                foreach (var coluna in linha.Keys)
                {
                    novaLinha[coluna] = ValorLegivel(linha[coluna]);
                }
                tabela.Rows.Add(novaLinha);
            }

            dgvResultados.DataSource = tabela;
            dgvResultados.AutoResizeColumns();
        }

        /// <summary>
        /// Converte um JsonElement em texto legível, tratando null
        /// separadamente (senão aparece "null" literal na grade).
        /// </summary>
        private static object ValorLegivel(JsonElement elemento)
        {
            if (elemento.ValueKind == JsonValueKind.Null) return DBNull.Value;
            return elemento.ToString();
        }
    }
}