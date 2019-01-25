package coffee_management.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import coffee_management.dto.Product;
import coffee_management.dto.Sale;
import coffee_management.service.CoffeeManagementService;
import coffee_management.service.OutputService;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CoffeeManagementUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tfCode;
	private JTextField tfName;
	private JTextField tfPrice;
	private JTextField tfSaleCnt;
	private JTextField tfMarginRate;

	//서비스
	private CoffeeManagementService saleService;
	//버튼
	private JButton btnInput;
	private JButton btnSalePrice;
	private JButton btnMarginPrice;
	
	/**
	 * Create the frame.
	 */
	public CoffeeManagementUI() {
		saleService = new CoffeeManagementService();
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		setTitle("김보민_판매실적 계산 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		JPanel pContent = new JPanel();
		contentPane.add(pContent, BorderLayout.CENTER);
		pContent.setLayout(new GridLayout(0, 4, 10, 10));
		
		JLabel lblCode = new JLabel("제품코드");
		lblCode.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblCode.setHorizontalAlignment(SwingConstants.CENTER);
		pContent.add(lblCode);
		
		tfCode = new JTextField();
		tfCode.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(tfCode);
		tfCode.setColumns(10);
		
		JLabel lblName = new JLabel("제품명");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(lblName);
		
		//제품명
		tfName = new JTextField();
		tfName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		tfName.setEnabled(false);
		pContent.add(tfName);
		tfName.setColumns(10);
		
		JLabel lblPrice = new JLabel("제품단가");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(lblPrice);
		
		tfPrice = new JTextField();
		tfPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(tfPrice);
		tfPrice.setColumns(10);
		
		JLabel lblBlank0 = new JLabel("");
		pContent.add(lblBlank0);
		
		JLabel lblBlank1 = new JLabel("");
		pContent.add(lblBlank1);
		
		JLabel lblSaleCnt = new JLabel("판매수량");
		lblSaleCnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaleCnt.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(lblSaleCnt);
		
		tfSaleCnt = new JTextField();
		tfSaleCnt.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(tfSaleCnt);
		tfSaleCnt.setColumns(10);
		
		JLabel lblBlank2 = new JLabel("");
		pContent.add(lblBlank2);
		
		JLabel lblBlank3 = new JLabel("");
		pContent.add(lblBlank3);
		
		JLabel lblMarginRate = new JLabel("마진율");
		lblMarginRate.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarginRate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(lblMarginRate);
		
		tfMarginRate = new JTextField();
		tfMarginRate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pContent.add(tfMarginRate);
		tfMarginRate.setColumns(10);
		
		JPanel pButton = new JPanel();
		contentPane.add(pButton, BorderLayout.SOUTH);
		
		btnInput = new JButton("입력");
		btnInput.addActionListener(this);
		btnInput.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pButton.add(btnInput);
		
		btnSalePrice = new JButton("출력1");
		btnSalePrice.addActionListener(this);
		btnSalePrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pButton.add(btnSalePrice);
		
		btnMarginPrice = new JButton("출력2");
		btnMarginPrice.addActionListener(this);
		btnMarginPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		pButton.add(btnMarginPrice);
		
		//제품코드 입력하면 제품명 자동으로 나오게 한다.
		tfCode.getDocument().addDocumentListener(new MyDocumentListener() {
			
			@Override
			public void msg() {
				// TODO Auto-generated method stub
				if(tfCode.getText().length() == 4) {
					Product product = new Product(tfCode.getText().trim());
					
					try {
						Product searchProduct = saleService.searchProduct(product);
						System.out.println("검색한 제품명 : " + searchProduct);
						tfName.setText(searchProduct.getName());
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (NullPointerException e) {
						tfName.setText("해당 제품 없음");
					}					
				}
			}
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnMarginPrice) {
			do_btnMarginPrice_actionPerformed(arg0);
		}
		if (arg0.getSource() == btnSalePrice) {
			do_btnSalePrice_actionPerformed(arg0);
		}
		if (arg0.getSource() == btnInput) {
			do_btnInput_actionPerformed(arg0);
		}
	}
	
	//입력버튼 : 입력한 데이터들이 Sale 테이블에 저장된다.
	protected void do_btnInput_actionPerformed(ActionEvent arg0) {
		// 텍스트필드에 있는 값들을 들고와서 Sale에 저장.
		
		int res;
		
		try {
			//제품단가
			if(tfPrice.getText().length() > 9) {
				JOptionPane.showMessageDialog(null, "8자리 이내의 정수를 입력해주세요.");
				tfPrice.setText("");
				
				try {
					int price = Integer.parseInt(tfPrice.getText());
					if(price <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfPrice.setText("");
					
					return;
				}
			}else {
				try {
					int price = Integer.parseInt(tfPrice.getText());
					if(price <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfPrice.setText("");
					
					return;
				}
			}
			
			//판매수량
			if(tfSaleCnt.getText().length() > 9) {
				JOptionPane.showMessageDialog(null, "8자리 이내의 정수를 입력해주세요.");
				tfSaleCnt.setText("");
				
				try {
					int saleCnt = Integer.parseInt(tfSaleCnt.getText());
					if(saleCnt <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfSaleCnt.setText("");
					
					return;
				}
			} else {
				try {
					int saleCnt = Integer.parseInt(tfSaleCnt.getText());
					if(saleCnt <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfSaleCnt.setText("");
					
					return;
				}
			}
			
			//마진율
			if(tfMarginRate.getText().length() > 3) {
				JOptionPane.showMessageDialog(null, "2자리 이내의 정수를 입력해주세요.");
				tfMarginRate.setText("");
				
				try {
					int marginRate = Integer.parseInt(tfPrice.getText());
					
					if(marginRate <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfMarginRate.setText("");
					
					return;
				}
			} else {
				try {
					int marginRate = Integer.parseInt(tfMarginRate.getText());
					
					if(marginRate <= 0) {
						JOptionPane.showMessageDialog(null, "양수만 입력해주세요.");
						return;
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "정수를 입력해주세요.");
					tfMarginRate.setText("");
				}
			}
			
			Sale sale = getSale();
			res = saleService.registerSale(sale);
			
			if(res == 1) {
				JOptionPane.showMessageDialog(null, "제품을 추가했습니다.");
			}
			clearTf();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//텍스트필드에 있는 값 가져와서 Sale에다가 넣기
	private Sale getSale() {
		// TODO Auto-generated method stub
		String code = tfCode.getText().trim();
		int price = Integer.parseInt(tfPrice.getText().trim());
		int saleCnt = Integer.parseInt(tfSaleCnt.getText().trim());
		int marginRate = Integer.parseInt(tfMarginRate.getText().trim());
		
		return new Sale(0, new Product(code), price, saleCnt, marginRate);
	}

	//텍스트필드 초기화하기
	private void clearTf() {
		// TODO Auto-generated method stub
		tfCode.setText("");
		tfPrice.setText("");
		tfName.setText("");
		tfSaleCnt.setText("");
		tfMarginRate.setText("");
	}
	
	//출력1
	protected void do_btnSalePrice_actionPerformed(ActionEvent arg0) {
		OutputUI ui = new OutputUI(true);
		ui.setVisible(true);
	}
	
	//출력2
	protected void do_btnMarginPrice_actionPerformed(ActionEvent arg0) {
		OutputUI ui = new OutputUI(false);
		ui.setVisible(true);
	}
}
