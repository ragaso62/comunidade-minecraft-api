namespace GrimDashboard
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        private System.Windows.Forms.ComboBox cmbRelatorio;
        private System.Windows.Forms.TextBox txtNickname;
        private System.Windows.Forms.Button btnCarregar;
        private System.Windows.Forms.DataGridView dgvResultados;
        private System.Windows.Forms.Panel panelTopo;
        private System.Windows.Forms.Label lblNickname;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.cmbRelatorio = new System.Windows.Forms.ComboBox();
            this.txtNickname = new System.Windows.Forms.TextBox();
            this.btnCarregar = new System.Windows.Forms.Button();
            this.dgvResultados = new System.Windows.Forms.DataGridView();
            this.panelTopo = new System.Windows.Forms.Panel();
            this.lblNickname = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvResultados)).BeginInit();
            this.panelTopo.SuspendLayout();
            this.SuspendLayout();
            //
            // cmbRelatorio
            //
            this.cmbRelatorio.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbRelatorio.Location = new System.Drawing.Point(12, 15);
            this.cmbRelatorio.Name = "cmbRelatorio";
            this.cmbRelatorio.Size = new System.Drawing.Size(320, 23);
            this.cmbRelatorio.TabIndex = 0;
            //
            // lblNickname
            //
            this.lblNickname.AutoSize = true;
            this.lblNickname.Location = new System.Drawing.Point(345, 18);
            this.lblNickname.Name = "lblNickname";
            this.lblNickname.Size = new System.Drawing.Size(60, 15);
            this.lblNickname.TabIndex = 3;
            this.lblNickname.Text = "Nickname:";
            //
            // txtNickname
            //
            this.txtNickname.Location = new System.Drawing.Point(411, 15);
            this.txtNickname.Name = "txtNickname";
            this.txtNickname.Size = new System.Drawing.Size(180, 23);
            this.txtNickname.TabIndex = 1;
            //
            // btnCarregar
            //
            this.btnCarregar.Location = new System.Drawing.Point(605, 14);
            this.btnCarregar.Name = "btnCarregar";
            this.btnCarregar.Size = new System.Drawing.Size(100, 25);
            this.btnCarregar.TabIndex = 2;
            this.btnCarregar.Text = "Carregar";
            this.btnCarregar.UseVisualStyleBackColor = true;
            this.btnCarregar.Click += new System.EventHandler(this.btnCarregar_Click);
            //
            // panelTopo
            //
            this.panelTopo.Controls.Add(this.cmbRelatorio);
            this.panelTopo.Controls.Add(this.lblNickname);
            this.panelTopo.Controls.Add(this.txtNickname);
            this.panelTopo.Controls.Add(this.btnCarregar);
            this.panelTopo.Dock = System.Windows.Forms.DockStyle.Top;
            this.panelTopo.Location = new System.Drawing.Point(0, 0);
            this.panelTopo.Name = "panelTopo";
            this.panelTopo.Size = new System.Drawing.Size(984, 55);
            this.panelTopo.TabIndex = 0;
            //
            // dgvResultados
            //
            this.dgvResultados.AllowUserToAddRows = false;
            this.dgvResultados.AllowUserToDeleteRows = false;
            this.dgvResultados.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvResultados.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvResultados.Location = new System.Drawing.Point(0, 55);
            this.dgvResultados.Name = "dgvResultados";
            this.dgvResultados.ReadOnly = true;
            this.dgvResultados.RowHeadersWidth = 51;
            this.dgvResultados.Size = new System.Drawing.Size(984, 506);
            this.dgvResultados.TabIndex = 1;
            //
            // Form1
            //
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(984, 561);
            this.Controls.Add(this.dgvResultados);
            this.Controls.Add(this.panelTopo);
            this.Name = "Form1";
            this.Text = "GrimDashboard";
            ((System.ComponentModel.ISupportInitialize)(this.dgvResultados)).EndInit();
            this.panelTopo.ResumeLayout(false);
            this.panelTopo.PerformLayout();
            this.ResumeLayout(false);
        }

        #endregion
    }
}