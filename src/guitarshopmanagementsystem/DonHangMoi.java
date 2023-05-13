package guitarshopmanagementsystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import java.awt.Window.Type;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.awt.Font;

public class DonHangMoi extends JFrame {

	private JPanel contentPane;
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JTextField txtDiaChi;
	private JPanel panel;
	private JTextField txtSoLuong;
	private JTable tbTaoDonHang;
	private String currentOrderId; 
	private long total;
	private String currentCustomerId;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DonHangMoi frame = new DonHangMoi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DonHangMoi() {
		setTitle("Tạo đơn hàng");
		setType(Type.POPUP);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		
		panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTenKhachHang = new JLabel("Tên khách hàng:");
		
		JLabel lblSdt = new JLabel("Số điện thoại:");
		
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		
		txtTenKH = new JTextField();
		txtTenKH.setColumns(10);
		
		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		
		JLabel lblSanPham = new JLabel("Sản phẩm:");
		
		JComboBox cbLoai = new JComboBox();
		cbLoai.setModel(new DefaultComboBoxModel(new String[] {"Chọn loại","Acoustic", "Classic", "Electric", "Bass", "Phụ kiện"}));
		
		JComboBox cbSP = new JComboBox();
		cbSP.setModel(new DefaultComboBoxModel(new String[] {"Chọn sản phẩm"}));
	
		cbLoai.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				try {
					if(e.getStateChange() == ItemEvent.SELECTED) {
			            String selectedValue = cbLoai.getSelectedItem().toString();
			            Connection con = JDBCConnection.getJDBCConnection();
			            String sql = "select * from products where type =?";
			            PreparedStatement ps = con.prepareStatement(sql);
			            ps.setString(1, selectedValue);
			            cbSP.removeAllItems();
			            
			            ResultSet rs = ps.executeQuery();
			            cbSP.removeAllItems();
			            
			            while (rs.next()) {
			                String productName = rs.getString("name");
			                cbSP.addItem(productName);
			            }
			            
			            rs.close();
			            ps.close();
			            con.close();
					}
				}catch(Exception ex) {}
			}
		});
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("");
		
		JLabel lblSoLuong = new JLabel("Số lượng:");
		
		JButton btnThemSP = new JButton("Thêm sản phẩm");
		
		JLabel lblTongTien = new JLabel("0 VNĐ");
		lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tbTaoDonHang = new JTable();
		tbTaoDonHang.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"T\u00EAn SP", "S\u1ED1 l\u01B0\u1EE3ng", "Th\u00E0nh ti\u1EC1n"
			}
		));
		
		btnThemSP.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                Connection con = JDBCConnection.getJDBCConnection();
	                if (!txtTenKH.getText().isEmpty() && !txtSDT.getText().isEmpty() && !txtSDT.getText().isEmpty()
	                        && !txtDiaChi.getText().isEmpty() && !txtSoLuong.getText().isEmpty()) {
	                    if (!cbLoai.getSelectedItem().equals("Chọn loại")) {
	                        if (currentOrderId == null && currentCustomerId == null) {
	                            // Tạo mới id_order
	                            UUID uuid = UUID.randomUUID();
	                            currentOrderId = uuid.toString().substring(0, 8);
	                            
	                            // Lưu thông tin đơn hàng vào bảng orders
	                            LocalDate currentDate = LocalDate.now();
	                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                            String formattedDate = currentDate.format(formatter);
	                            String sql = "insert into orders(id_order, date, cus_name, cus_phone, cus_address)"
	                                    + "values(?, ?, ?, ?, ?)";
	                            PreparedStatement ps = con.prepareStatement(sql);
	                            ps.setString(1, currentOrderId);
	                            ps.setString(2, formattedDate);
	                            ps.setString(3, txtTenKH.getText());
	                            ps.setString(4, txtSDT.getText());
	                            ps.setString(5, txtDiaChi.getText());
	                            ps.executeUpdate();
	                            
	                            // Lưu thông tin khách hàng vào bảng customers
	                            UUID uuid1 = UUID.randomUUID();
	                            currentCustomerId = uuid1.toString().substring(0, 8);
	                            String sql7 = "insert into customers (id_cus, fullname, phone_number, address) values ('" + currentCustomerId + "', '" + txtTenKH.getText() + "', '" + txtSDT.getText() + "', '" + txtDiaChi.getText() + "');";
	                            Statement ps7 = con.createStatement();
	                            ps7.executeUpdate(sql7);
	                        }

	                        // Lấy id_product từ bảng products
	                        String sql1 = "select id_product from products where name = ?";
	                        PreparedStatement ps1 = con.prepareStatement(sql1);
	                        ps1.setString(1, cbSP.getSelectedItem().toString());
	                        ResultSet rs1 = ps1.executeQuery();
	                        String id_product_detail = "";
	                        if (rs1.next()) {
	                            id_product_detail = rs1.getString("id_product");
	                        }
	                        
	                        // 
	                        String sqlCheck2 = "SELECT quantity FROM products WHERE id_product = ?";
	                        PreparedStatement psCheck2 = con.prepareStatement(sqlCheck2);
	                        psCheck2.setString(1, id_product_detail);
	                        ResultSet rsCheck2 = psCheck2.executeQuery();
	                        if (rsCheck2.next()) {
	                            int quantityInStock = rsCheck2.getInt("quantity");
	                            int quantityToAdd = Integer.parseInt(txtSoLuong.getText());
	                            if (quantityToAdd > quantityInStock) {
	                                JOptionPane.showMessageDialog(null, "Số lượng vượt quá số lượng tồn kho (> " + quantityInStock + ")");
	                                return;
	                            }
	                        }
							
	                        // Kiểm tra xem sản phẩm đã tồn tại trong đơn hàng hay chưa
	                        String sqlCheck = "select count(*) as count from order_detail where id_order_detail = ? and id_product_detail = ?";
	                        PreparedStatement psCheck = con.prepareStatement(sqlCheck);
	                        psCheck.setString(1, currentOrderId);
	                        psCheck.setString(2, id_product_detail);
	                        ResultSet rsCheck = psCheck.executeQuery();
	                        if (rsCheck.next()) {
	                            int count = rsCheck.getInt("count");
	                            if (count > 0) {
	                                JOptionPane.showMessageDialog(null, "Sản phẩm đã tồn tại trong đơn hàng!");
	                                return;
	                            }
	                        }
	                        
	                        // Thêm sản phẩm vào bảng order_detail
	                        String sql2 = "insert into order_detail(id_order_detail, id_product_detail, quantity_detail)"
	                                + "values (?, ?, ?)";
	                        PreparedStatement ps2 = con.prepareStatement(sql2);
	                        ps2.setString(1, currentOrderId);
	                        ps2.setString(2, id_product_detail);
	                        int quantity = Integer.parseInt(txtSoLuong.getText());
	                        ps2.setInt(3, quantity);
	                        int res2 = ps2.executeUpdate();
	                        
	                        if (res2 > 0) {
	                            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại!");
	                        }
	                        
	                        // Lấy price từ bảng products để tính tổng tiền đơn hàng
	                        String sql3 = "select price from products where id_product = ?";
	                        PreparedStatement ps3 = con.prepareStatement(sql3);
	                        ps3.setString(1, id_product_detail);
	                        ResultSet rs3 = ps3.executeQuery();
	                        if (rs3.next()) {
	                            total += Integer.parseInt(rs3.getString("price")) * quantity;
	                        }
	                        lblTongTien.setText(Long.toString(total) + " VNĐ");
	                        //System.out.println(total);
	                        
	                        String sql4 = "update orders set total = ? where id_order = ?";
	                        PreparedStatement ps4 = con.prepareStatement(sql4);
	                        ps4.setLong(1, total);
	                        ps4.setString(2, currentOrderId);
	                        int res4 = ps4.executeUpdate();
	                        
	                        if (res4 > 0) {
	                            JOptionPane.showMessageDialog(null, "Cập nhật giá tiền thành công!");
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Cập nhật giá tiền thất bại!");
	                        }
	                        
	                        //Thêm vào table danh sách sản phẩm đã chọn
	                        String productName = cbSP.getSelectedItem().toString();
	                        
	                        DefaultTableModel model = (DefaultTableModel) tbTaoDonHang.getModel();
	                        if (model.getRowCount() == 1 && model.getValueAt(0, 0) == null) {
	                            // Hàng đầu tiên là trống, thêm thông tin sản phẩm vào hàng đó
	                            model.setValueAt(productName, 0, 0);
	                            model.setValueAt(quantity, 0, 1);
	                            model.setValueAt(Long.toString(total) + " VNĐ", 0, 2);
	                        } else {
	                            // Hàng đầu tiên đã có dữ liệu, thêm thông tin sản phẩm vào hàng mới
	                            model.addRow(new Object[]{productName, quantity, Integer.parseInt(rs3.getString("price")) * quantity + " VNĐ"});
	                        }
	                        
	                        // Lấy số lượng sản phẩm đã bán trong bảng order_detail 
	                        String sql5 = "select sum(quantity_detail) as sold_quantity from order_detail where id_product_detail = ? and id_order_detail = ?";
	                        PreparedStatement ps5 = con.prepareStatement(sql5);
	                        ps5.setString(1, id_product_detail);
	                        ps5.setString(2, currentOrderId);
	                        
	                        ResultSet rs5 = ps5.executeQuery();
	                        int soldQuantity = 0;
	                        if (rs5.next()) {
	                            soldQuantity = rs5.getInt("sold_quantity");
	                            System.out.println(soldQuantity); //done
	                        }
	                        
	                        // Lấy số lượng sản phẩm còn trong kho
	                        String sql_proquantity = "select quantity from products where id_product = ?";
	                        PreparedStatement ps_proquantity = con.prepareStatement(sql_proquantity);
	                        ps_proquantity.setString(1, id_product_detail);
	                        ResultSet res_proquantity = ps_proquantity.executeQuery();
	                        int quantity_product = 0;
	                        if (res_proquantity.next()) {
	                            quantity_product = res_proquantity.getInt("quantity");
	                            System.out.println(quantity_product);
	                        }

	                        // Cập nhật số lượng sản phẩm trong bảng products
	                        String sql6 = "update products set quantity = ? where id_product = ?";
	                        PreparedStatement ps6 = con.prepareStatement(sql6);
	                        ps6.setInt(1, quantity_product - soldQuantity);
	                        ps6.setString(2, id_product_detail);
	                        int res6 = ps6.executeUpdate();
	                        System.out.println(quantity_product - soldQuantity);
	                        if (res6 > 0) {
	                            JOptionPane.showMessageDialog(null, "Cập nhật số lượng sản phẩm thành công!");
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Cập nhật số lượng sản phẩm thất bại!");
	                        }
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm!");
	                        return;
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
	                    return;
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
		
		txtSoLuong = new JTextField();
		txtSoLuong.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnTaoDonHang = new JButton("Tạo đơn hàng");
		btnTaoDonHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        SwingUtilities.getWindowAncestor(btnTaoDonHang).dispose();
				DonHangJP dh = new DonHangJP();
				Home home = new Home();
		        home.setContentPane(dh);
		        // Hiển thị frame Home
		        home.setVisible(true);
			}
		});
		
		JLabel lbltongtien = new JLabel("Tổng tiền:");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTenKhachHang)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblSoLuong, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblSdt)
						.addComponent(lblDiaChi, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSanPham, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(txtSDT)
							.addComponent(txtTenKH, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addComponent(txtDiaChi, Alignment.TRAILING))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtSoLuong, Alignment.LEADING)
							.addComponent(cbLoai, Alignment.LEADING, 0, 137, Short.MAX_VALUE)))
					.addGap(38)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btnThemSP)
								.addGap(18)
								.addComponent(btnTaoDonHang, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
							.addComponent(cbSP, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lbltongtien, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTongTien)))
					.addGap(124))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1_1_1_1)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTenKhachHang)
								.addComponent(txtTenKH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSdt)
								.addComponent(txtSDT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDiaChi)
								.addComponent(txtDiaChi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbltongtien)
								.addComponent(lblTongTien))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(cbSP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSanPham)
								.addComponent(cbLoai, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSoLuong)
								.addComponent(txtSoLuong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnThemSP)
								.addComponent(btnTaoDonHang))))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(tbTaoDonHang);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
