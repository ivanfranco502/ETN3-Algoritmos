import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import java.io.*;
import java.net.*;

public class Algoritmos extends JFrame implements ActionListener,KeyListener{
	//COMPONENTES
	JTextField txtcarga;
	JButton btncarga;
	JButton btnguarda;
	JScrollPane scrtabla;
	JTable jtabla;
	JButton btnleer;
	JButton btngrafico;
	JLabel moda;
	JLabel promedio;
	JLabel mediana;
	//VARIABLES
	Vector vector;
	GrafBarras gb= new GrafBarras();
    GrafTorta gt= new GrafTorta();
    JFrame jfBarras;
    JFrame jfTorta;

	public Algoritmos(){
		//INICIALIZACION COMPONENTES
		txtcarga=new JTextField();
		btncarga=new JButton("Cargar Valor");
		btnguarda=new JButton("Guardar Archivo");
		btnleer=new JButton("Cargar Archivo");
		btngrafico=new JButton("Graficos");
		jtabla=new JTable(100,5);
		scrtabla=new JScrollPane(jtabla);
		moda=new JLabel("MODA: ");
		promedio=new JLabel("PROMEDIO: ");
		mediana=new JLabel("MEDIANA: ");

		//INICIALIZACION VARIABLES
		vector=new Vector();
		//INTERFAZ
		setLayout(null);
		txtcarga.setBounds(10,30,150,20);
		btncarga.setBounds(10,70,150, 30);
		btngrafico.setBounds(10,120,150,30);
		btnguarda.setBounds(10,170,150,30);
		btnleer.setBounds(10,220,150,30);
		scrtabla.setBounds(200,30,350,270);
		moda.setBounds(10,330,150,20);
		promedio.setBounds(180,330,150,20);
		mediana.setBounds(380,330,150,20);

		add(txtcarga);
		add(btncarga);
		add(btngrafico);
		add(btnguarda);
		add(btnleer);
		add(scrtabla);
		add(moda);
		add(promedio);
		add(mediana);

		btncarga.addActionListener(this);
		btnguarda.addActionListener(this);
		btnleer.addActionListener(this);
		btngrafico.addActionListener(this);
		txtcarga.addKeyListener(this);

		setSize(600,400);
		setVisible(true);
	}

	public int buscarFin(){
		int contador=0,indice=0;

		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			contador++;
			indice++;
		}
		return(contador);
	}
	public void cargarDato(){
		int valor=0,indice=0,cantidad=0,acumulada=0;
		float fr=(float)0,fri=(float)0;
		boolean entro=false;
		int fin=0,total=0;
		int aux=0;
		String dato;
		valor=Integer.parseInt(txtcarga.getText());

		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			dato=String.valueOf(jtabla.getValueAt(indice,0)).toString();
			if(Integer.parseInt(dato)==valor){
				entro=true;
				dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
				cantidad=Integer.parseInt(dato);
				cantidad++;
				jtabla.setValueAt(cantidad,indice,1);
				try{
					dato=String.valueOf(jtabla.getValueAt(indice-1,2)).toString();
					acumulada=Integer.parseInt(dato);
					acumulada+=cantidad;
					jtabla.setValueAt(acumulada,indice,2);
				}catch(RuntimeException re){
					acumulada=cantidad;
					jtabla.setValueAt(acumulada,indice,2);
				}
				actualizarFrecuencias();
				fin=buscarFin();

				dato=String.valueOf(jtabla.getValueAt(fin-1,2)).toString();
				total=Integer.parseInt(dato);

				dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
				fr=Float.parseFloat(dato);
				fr/=total;
				jtabla.setValueAt(fr,indice,3);

				dato=String.valueOf(jtabla.getValueAt(indice,2)).toString();
				fri=Float.parseFloat(dato);
				fri/=total;
				jtabla.setValueAt(fri,indice,4);

			}
			indice++;
		}
		//System.out.println(entro);
		if(!entro){
			aux=buscarFin();
			jtabla.setValueAt(valor,aux,0);
			jtabla.setValueAt(1,aux,1);
			try{
				dato=String.valueOf(jtabla.getValueAt(aux-1,2)).toString();
				acumulada=Integer.parseInt(dato);
				acumulada++;
				jtabla.setValueAt(acumulada,aux,2);
			}catch(RuntimeException re){
				acumulada=1;
				jtabla.setValueAt(acumulada,aux,2);
			}
			dato=String.valueOf(jtabla.getValueAt(aux,2)).toString();
			total=Integer.parseInt(dato);

			dato=String.valueOf(jtabla.getValueAt(aux,1)).toString();
			fr=Float.parseFloat(dato);
			fr/=total;
			jtabla.setValueAt(fr,aux,3);

			dato=String.valueOf(jtabla.getValueAt(aux,2)).toString();
			fri=Float.parseFloat(dato);
			fri/=total;
			jtabla.setValueAt(fri,aux,4);


		}
		actualizarFrecuencias();
		buscarModa();
		buscarPromedio();
		buscarMediana();
	}

	public void buscarMediana(){
		int indice=0;
		float valor=(float)0;
		boolean esta=false;

		mediana.setText("MEDIANA: ");
		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				valor=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,4)).toString());
				if(valor>=0.50 && !esta){
					esta=true;
					mediana.setText("MEDIANA: "+String.valueOf(jtabla.getValueAt(indice,0)).toString());
				}
			indice++;
		}
	}
	public void buscarPromedio(){
		int fin=0;
		int suma=0;
		int indice=0;

		promedio.setText("PROMEDIO: ");
		fin=Integer.parseInt(String.valueOf(jtabla.getValueAt(buscarFin()-1,2)).toString());
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				suma+=((Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,0)).toString()))*(Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString())));
				indice++;
			}
			suma/=fin;
			promedio.setText(promedio.getText()+String.valueOf(suma).toString());
	}

	public void buscarModa(){
		int []mod=new int[2];
		int []aux=new int[2];
		int indice=0;

		moda.setText("MODA: ");
		mod[0]=Integer.parseInt(String.valueOf(jtabla.getValueAt(0,0)).toString());
		mod[1]=Integer.parseInt(String.valueOf(jtabla.getValueAt(0,1)).toString());

		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			aux[0]=Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,0)).toString());
			aux[1]=Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString());
			if(mod[1]<aux[1]){
				mod[0]=aux[0];
				mod[1]=aux[1];
			}
			indice++;
		}
		moda.setText(moda.getText()+String.valueOf(mod[0]).toString());
	}

	public void actualizarFrecuencias(){
		int indice=0,acumulada=0,cantidad=0;
		float fr=(float)0,fri=(float)0;
		int fin=0,total=0;
		String dato;

		indice=0;
		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
			cantidad=Integer.parseInt(dato);
			try{
				dato=String.valueOf(jtabla.getValueAt(indice-1,2)).toString();
				acumulada=Integer.parseInt(dato);
				acumulada+=cantidad;
				jtabla.setValueAt(acumulada,indice,2);
			}catch(RuntimeException re){
				acumulada=cantidad;
				jtabla.setValueAt(acumulada,indice,2);
			}
			fin=buscarFin();
			//System.out.println(String.valueOf(fin).toString());
			dato=String.valueOf(jtabla.getValueAt(fin-1,2)).toString();
			total=Integer.parseInt(dato);

			dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
			fr=Float.parseFloat(dato);
			fr/=total;
			jtabla.setValueAt(fr,indice,3);

			dato=String.valueOf(jtabla.getValueAt(indice,2)).toString();
			fri=Float.parseFloat(dato);
			fri/=total;
			jtabla.setValueAt(fri,indice,4);

			indice++;
		}
	}
	public void ordenarVector(Vector v, Vector f){
		int x,y;
		int num=0,num1=0,aux=0;
		String valor,valor1;

		for(x=0;x<v.size();x++){
			for(y=0;y<v.size();y++){
				valor=String.valueOf(v.elementAt(x)).toString();
				valor1=String.valueOf(v.elementAt(y)).toString();
				num=Integer.parseInt(valor);
				num1=Integer.parseInt(valor1);
				if(num>num1){
					v.setElementAt(num,y);
					v.setElementAt(num1,x);

					valor=String.valueOf(f.elementAt(x)).toString();
					valor1=String.valueOf(f.elementAt(y)).toString();
					num=Integer.parseInt(valor);
					num1=Integer.parseInt(valor1);

					f.setElementAt(num,y);
					f.setElementAt(num1,x);

				}
			}
		}
	}
	public void mostrarGrafico(){
		int []v;
		int []f;
		int indice=0;
		int x,y;
		int num=0,num1=0;

		v=new int[100];
		f=new int[100];

		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			v[indice]=Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,0)).toString());
			f[indice]=Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString());
			indice++;
		}

		System.out.println(String.valueOf(indice).toString());
		if(indice>1){
			for(x=0;x<indice-1;x++){
				for(y=x+1;y<indice;y++){
					/*System.out.print(String.valueOf(v[x]).toString());
					System.out.println(String.valueOf(v[y]).toString());*/
					if(v[x]>v[y]){

						num=v[x];
						v[x]=v[y];
						v[y]=num;

						/*System.out.print(String.valueOf(num).toString());
						System.out.print(String.valueOf(v[x]).toString());
						System.out.println(String.valueOf(v[y]).toString());

						System.out.print(String.valueOf(f[x]).toString());
						System.out.println(String.valueOf(f[y]).toString());
						*/
						num1=f[x];
						f[x]=f[y];
						f[y]=num1;
						/*
						System.out.print(String.valueOf(num1).toString());
						System.out.print(String.valueOf(f[x]).toString());
						System.out.println(String.valueOf(f[y]).toString());
						*/
					}
				}
			}
		}else{
			//FUNCION GRAFICAR (V,F)
		}
		/*
		System.out.print(String.valueOf(v[0]).toString());
		System.out.println(String.valueOf(f[0]).toString());
		System.out.print(String.valueOf(v[1]).toString());
		System.out.println(String.valueOf(f[1]).toString());
		*/
		jfBarras = new JFrame();
		jfBarras.setVisible(true);
		jfBarras.setSize(400,400);
		jfBarras.add(gb);

		gb.setValues(v,f);
		gb.repaint();

		jfTorta = new JFrame();
		jfTorta.setVisible(true);
		jfTorta.setSize(400,400);
		jfTorta.add(gt);

		gt.setValues(v,f);
		gt.repaint();
		//FUNCION GRAFICAR (V,F)
	}
	public void guardarDatos(){
		JOptionPane aviso=new JOptionPane();
		int indice=0,columna=0;

		vector.clear();
		while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
			vector.addElement(jtabla.getValueAt(indice,columna));
			columna++;
			if(columna>4){
				indice++;
				columna=0;
			}
		}
		try{
			File f=new File("U:\\Algoritmos\\algoritmos.tp");
			ObjectOutputStream ois=new ObjectOutputStream(new FileOutputStream(f));
			if(buscarFin()>0){
				ois.writeObject(vector);
				aviso.showMessageDialog(null,"Se ha guardado el archivo exitosamente","Archivo guardado",aviso.INFORMATION_MESSAGE);
				ois.close();
			}else{
				aviso.showMessageDialog(null,"Se ha producido un error imprevisto","Error Archivo",aviso.ERROR_MESSAGE);
			}
		}catch(IOException ioe){
			aviso.showMessageDialog(null,"Se ha producido un error imprevisto","Error Archivo",aviso.ERROR_MESSAGE);
        }
	}
	public void leerDatos(){
		JOptionPane aviso=new JOptionPane();
		int indice=0,columna=0,contador=0;

		vector.clear();

		remove(scrtabla);
		jtabla=new JTable(100,5);
		scrtabla=new JScrollPane(jtabla);
		scrtabla.setBounds(200,30,350,270);
		add(scrtabla);

		try{
			File f=new File("U:\\Algoritmos\\algoritmos.tp");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			vector=(Vector)ois.readObject();
			//System.out.println(String.valueOf(vector.size()).toString());
			while(contador<vector.size()){
				jtabla.setValueAt(vector.elementAt(contador),indice,columna);
				contador++;
				columna++;
				if(columna>4){
					indice++;
					columna=0;
				}
			}
			buscarModa();
			buscarPromedio();
			buscarMediana();
			//System.out.println(String.valueOf(contador).toString());
			ois.close();
		}catch(IOException ioe){
			aviso.showMessageDialog(null,"El archivo no existe, debe crear uno primero.","Error Archivo",aviso.ERROR_MESSAGE);
			//guardarDatos();
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
			aviso.showMessageDialog(null,"Se ha producido un error imprevisto","Error Archivo",aviso.ERROR_MESSAGE);
        }
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==btncarga){
			cargarDato();
			txtcarga.setText("");
		}else{
			if(ae.getSource()==btngrafico){
				mostrarGrafico();
			}else{
				if(ae.getSource()==btnguarda){
					guardarDatos();
				}else{
					if(ae.getSource()==btnleer){
						leerDatos();
					}
				}
			}
		}
	}

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			cargarDato();
			txtcarga.setText("");
		}
	}

	public static void main(String []s){
		Algoritmos algo=new Algoritmos();
		algo.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}