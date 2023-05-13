/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guitarshopmanagementsystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author quoca
 */
public class DoiMatKhau extends JFrame implements ActionListener, KeyListener{

    /**
     * Creates new form DoiMatKhau
     */
    public DoiMatKhau() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    public void initComponents() {

        jPanel1 = new JPanel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        txtTaiKhoanDMK = new JTextField();
        txtMatKhauCu = new JTextField();
        txtMatKhauMoi = new JTextField();
        btnXacNhan = new JButton();
        btnHuy = new JButton();
        chckbxDuyTri = new JCheckBox("Duy trì đăng nhập");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jLabel5.setText("ĐỔI MẬT KHẨU");

        jLabel6.setText("Tài khoản:");

        jLabel7.setText("Mật khẩu cũ:");

        jLabel8.setText("Mật khẩu mới:");

        btnXacNhan.setText("Xác nhận");
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(this);
        btnXacNhan.addActionListener(this);

        txtTaiKhoanDMK.addKeyListener(this);
        txtMatKhauCu.addKeyListener(this);
        txtMatKhauMoi.addKeyListener(this);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap(67, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel8)
        				.addComponent(jLabel6, Alignment.LEADING)
        				.addComponent(jLabel7, Alignment.LEADING))
        			.addGap(14)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(chckbxDuyTri)
        				.addComponent(jLabel5)
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(txtTaiKhoanDMK)
        					.addComponent(txtMatKhauCu, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        					.addComponent(txtMatKhauMoi)))
        			.addGap(58))
        		.addGroup(Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        			.addGap(94)
        			.addComponent(btnXacNhan)
        			.addGap(55)
        			.addComponent(btnHuy)
        			.addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(47)
        			.addComponent(jLabel5)
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel6)
        				.addComponent(txtTaiKhoanDMK, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel7)
        				.addComponent(txtMatKhauCu, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel8)
        				.addComponent(txtMatKhauMoi, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(chckbxDuyTri)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnXacNhan)
        				.addComponent(btnHuy))
        			.addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    public void actionPerformed(ActionEvent evt) {
    	if(evt.getSource() == btnXacNhan) {
    		try {
    			Connection con = JDBCConnection.getJDBCConnection();

                // Truy vấn để lấy mật khẩu hiện tại của người dùng
                String sql1 = "SELECT password FROM log_in WHERE username=?";
                PreparedStatement ps1 = con.prepareCall(sql1);
                ps1.setString(1, txtTaiKhoanDMK.getText());
                ResultSet rs1 = ps1.executeQuery();
                
            	if(txtTaiKhoanDMK.getText().equals("") || txtMatKhauCu.getText().equals("") || txtMatKhauMoi.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; 
            	}
                if (!rs1.next() || !rs1.getString("password").equals(txtMatKhauCu.getText())) {
                    // Nếu mật khẩu hiện tại không trùng khớp với mật khẩu cũ
                    JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Nếu mật khẩu cũ đúng, thực hiện truy vấn cập nhật mật khẩu mới
                String sql2 = "UPDATE log_in SET password=? WHERE username=?";
                PreparedStatement ps2 = con.prepareCall(sql2);
                ps2.setString(1, txtMatKhauMoi.getText());
                ps2.setString(2, txtTaiKhoanDMK.getText());
                int rowsAffected = ps2.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    if(chckbxDuyTri.isSelected()) {
	                    Home home = new Home();
	                    home.setVisible(true);
	                    this.dispose();
                    }
                    else {
                    	DangNhap dangnhap = new DangNhap();
                        dangnhap.setVisible(true);
                        this.dispose();
                    }
                }
                else {
                	JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {}
    	}
    	if(evt.getSource() == btnHuy) {
    		Home home = new Home();
            home.setVisible(true);
            this.dispose();
    	}
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoiMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnHuy;
    private JButton btnXacNhan;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JTextField txtMatKhauCu;
    private JTextField txtMatKhauMoi;
    private JTextField txtTaiKhoanDMK;
    private JCheckBox chckbxDuyTri;

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnXacNhan.doClick();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			btnHuy.doClick();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated me
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
