import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import net.proteanit.sql.DbUtils;
import javax.swing.border.EtchedBorder;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SatishBook {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SatishBook window = new SatishBook();
					window.frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error meggasge: "+e.getMessage());	
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SatishBook() {
		initialize();
		connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/gvm", "satish","12345678"); bash login info
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Booklibrary", "root", "123456");
		} catch (Exception e) {
			System.out.println("Error meggasge: "+e.getMessage());	
		}
	}

	public void table_load() {
		try {
			pst = con.prepareStatement("Select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			System.out.println("Error meggasge: " + e.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 963, 623);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(347, 0, 223, 55);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(48, 85, 495, 220);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(37, 47, 142, 25);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Ediation");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(37, 98, 142, 25);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(37, 152, 142, 25);
		panel.add(lblNewLabel_1_2);

		txtbname = new JTextField();
		txtbname.setBounds(199, 51, 192, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);

		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(199, 102, 192, 20);
		panel.add(txtedition);

		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(199, 156, 192, 20);
		panel.add(txtprice);

		table = new JTable();
		table.setBounds(526, 359, 308, -215);
		frame.getContentPane().add(table);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();

				try {
					pst = con.prepareStatement(" insert into book (name ,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "recored added");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();

				} catch (SQLException e2) {
					System.out.println("Error meggasge: "+e2.getMessage());	
				}
			}
		});
		btnNewButton.setBounds(62, 336, 123, 48);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(243, 336, 112, 49);
		frame.getContentPane().add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnEdi = new JButton("Clear");
		btnEdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnEdi.setBounds(400, 337, 112, 46);
		frame.getContentPane().add(btnEdi);
		btnEdi.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(48, 410, 495, 98);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_1_1 = new JLabel("Book Id");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(10, 38, 78, 25);
		panel_1.add(lblNewLabel_1_1_1);

		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String id = txtbid.getText();
				try {
					pst = con.prepareStatement("select name,edition,price from book where id= ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					if (rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);

						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					} else {

						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(156, 42, 217, 20);
		panel_1.add(txtbid);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, bid;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtbid.getText();

				try {
					pst = con.prepareStatement(" update book set name=?,edition=?,price=? where id= ?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "recored updated");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();

				} catch (SQLException e2) {
					System.out.println(e2);
				}

			}

		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(609, 437, 112, 55);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bid;
				bid = txtbid.getText();

				try {
					pst = con.prepareStatement(" delete from book where id= ?");

					pst.setString(1, bid);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "recored deleted!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();

				} catch (SQLException e2) {
					System.out.println("Error meggasge: "+e2.getMessage());	
				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(778, 437, 112, 55);
		frame.getContentPane().add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(569, 97, 370, 251);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
