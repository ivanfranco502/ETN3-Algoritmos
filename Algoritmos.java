import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import java.io.*;
import java.net.*;

public class Algoritmos extends JFrame implements ActionListener,KeyListener{
	//COMPONENTES
	static String modo="s";
	JTextField txtcarga;
	JButton btnguarda;
	JScrollPane scrtabla;
	JTable jtabla;
	JButton btnleer;
	JButton btngrafico;
	JButton analizar;
	JLabel moda;
	JLabel promedio;
	JLabel mediana;
	JLabel desviacion;
	JLabel coeficiente;
	JLabel etiqueta;
	JButton simple;
	JButton intervalo;
	JButton nuevo;
	JTextField cantintervalo;
	JButton cargarintervalo;
	//VARIABLES
	Vector vector;
	GrafBarras gb= new GrafBarras();
	GrafTorta gt= new GrafTorta();
	JFrame jfBarras;
	JFrame jfTorta;
	float []inicio=new float[50];
	float []fina=new float[50];

/******************************************CONSTRUCTOR*************************************************/
	public Algoritmos(){
		//INICIALIZACION COMPONENTES
		txtcarga=new JTextField();
		analizar=new JButton("Analizar Datos");
		btnguarda=new JButton("Guardar Archivo");
		btnleer=new JButton("Cargar Archivo");
		btngrafico=new JButton("Graficos");
		jtabla=new JTable(100,6);
		scrtabla=new JScrollPane(jtabla);
		moda=new JLabel("MODA: ");
		promedio=new JLabel("PROMEDIO: ");
		mediana=new JLabel("MEDIANA: ");
		desviacion=new JLabel("DESVIACION: ");
		coeficiente=new JLabel("COEFICIENTE: ");
		etiqueta=new JLabel("x:               fi:               Fi:              fri:             Fri:    (x-prom)^2 fi:");
		simple=new JButton ("Tabla Simple");
		intervalo=new JButton("Tabla Intervalo");
		nuevo=new JButton("Nuevo");
		cantintervalo=new JTextField("");
		cargarintervalo=new JButton("Cargar Intervalos");

		//INICIALIZACION VARIABLES
		vector=new Vector();
		//INTERFAZ
		setLayout(null);
		txtcarga.setBounds(10,30,150,20);
		analizar.setBounds(10,70,150, 30);
		btngrafico.setBounds(10,120,150,30);
		btnguarda.setBounds(10,170,150,30);
		btnleer.setBounds(10,220,150,30);
		etiqueta.setBounds(225,13,350,20);
		etiqueta.setForeground(Color.DARK_GRAY);
		etiqueta.setBackground(Color.DARK_GRAY);
		scrtabla.setBounds(200,30,350,270);
		moda.setBounds(10,330,150,20);
		promedio.setBounds(180,330,150,20);
		mediana.setBounds(380,330,150,20);
		desviacion.setBounds(10,270,150,20);
		coeficiente.setBounds(10,300,150,20);
		nuevo.setBounds(570,50,150,30);
		simple.setBounds(570,100,150,30);
		intervalo.setBounds(570,150,150,30);
		cantintervalo.setBounds(570,200,150,20);
		cargarintervalo.setBounds(570,240,150,30);

		cantintervalo.setVisible(false);
		cargarintervalo.setVisible(false);

		add(txtcarga);
		add(analizar);
		add(btngrafico);
		add(btnguarda);
		add(btnleer);
		add(etiqueta);
		add(scrtabla);
		add(moda);
		add(promedio);
		add(mediana);
		add(desviacion);
		add(coeficiente);
		add(nuevo);
		add(simple);
		add(intervalo);
		add(cantintervalo);
		add(cargarintervalo);

		analizar.addActionListener(this);
		btnguarda.addActionListener(this);
		btnleer.addActionListener(this);
		btngrafico.addActionListener(this);
		txtcarga.addKeyListener(this);
		nuevo.addActionListener(this);
		simple.addActionListener(this);
		intervalo.addActionListener(this);
		cargarintervalo.addActionListener(this);

		setSize(750,400);
		setVisible(true);
	}
/******************************************BUSCAR FIN*************************************************/
	public int buscarFin(){
		int contador=0,indice=0;

			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				contador++;
				indice++;
			}

		return(contador);
	}
/******************************************Carga Dato***********************************************/
	public void cargarDato(){
		float valor=(float)0;
		int indice=0,cantidad=0,acumulada=0;
		float fr=(float)0,fri=(float)0;
		boolean entro=false;
		int fin=0,total=0;
		int aux=0;
		String dato;
		if(modo.compareTo("s")==0){
			valor=Float.parseFloat(txtcarga.getText());

			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				dato=String.valueOf(jtabla.getValueAt(indice,0)).toString();
				if(Float.parseFloat(dato)==valor){
					entro=true;
					dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
					cantidad=Integer.parseInt(dato);
					cantidad++;
					jtabla.setValueAt(cantidad,indice,1);
				}
				indice++;
			}
			//System.out.println(entro);
			if(!entro){
				aux=buscarFin();
				jtabla.setValueAt(valor,aux,0);
				jtabla.setValueAt(1,aux,1);
				ordenarTabla();
			}
		}
	}
/******************************************Ordena Tabla***********************************************/
	public void ordenarTabla(){
		int indice=0;
		String valor1,valor2;
		int x,y;
		Vector v=new Vector();
		Vector a=new Vector();
		if(modo.compareTo("s")==0){
			if(buscarFin()>0){
				for(x=0;x<buscarFin()-1;x++){
					for(y=x+1;y<buscarFin();y++){
						try{
							v.clear();
							valor1=String.valueOf(jtabla.getValueAt(x,0)).toString();
							valor2=String.valueOf(jtabla.getValueAt(y,0)).toString();
							if(Float.parseFloat(valor1)>Float.parseFloat(valor2)){
								v.addElement(jtabla.getValueAt(y,0));
								v.addElement(jtabla.getValueAt(y,1));
								v.addElement(jtabla.getValueAt(y,2));
								v.addElement(jtabla.getValueAt(y,3));
								v.addElement(jtabla.getValueAt(y,4));

								jtabla.setValueAt(jtabla.getValueAt(x,0),y,0);
								jtabla.setValueAt(jtabla.getValueAt(x,1),y,1);
								jtabla.setValueAt(jtabla.getValueAt(x,2),y,2);
								jtabla.setValueAt(jtabla.getValueAt(x,3),y,3);
								jtabla.setValueAt(jtabla.getValueAt(x,4),y,4);

								jtabla.setValueAt(v.elementAt(0),x,0);
								jtabla.setValueAt(v.elementAt(1),x,1);
								jtabla.setValueAt(v.elementAt(2),x,2);
								jtabla.setValueAt(v.elementAt(3),x,3);
								jtabla.setValueAt(v.elementAt(4),x,4);

							}
						}catch(RuntimeException re){

						}
					}
				}
			}
		}
	}
/******************************************Busca Mediana**********************************************/
	public void buscarMediana(){
		int indice=0;
		float valor=(float)0;
		boolean esta=false;
		int total=0;
		float amplitud=(float)0;
		if(modo.compareTo("s")==0){
			mediana.setText("MEDIANA: ");
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
					valor=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,4)).toString());
					if(valor>=0.50 && !esta){
						esta=true;
						mediana.setText("MEDIANA: "+String.valueOf(jtabla.getValueAt(indice,0)).toString());
					}
				indice++;
			}
		}else{
			total=Integer.parseInt(String.valueOf(jtabla.getValueAt(buscarFin()-1,2)).toString());
			System.out.println(String.valueOf(total).toString());
			amplitud=(fina[0]-inicio[0]);
			System.out.println(String.valueOf(amplitud).toString());
			mediana.setText("MEDIANA: ");
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				valor=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,4)).toString());
				if(valor>=0.50 && !esta){
					esta=true;
					total/=2;
					total*=amplitud;
					total/=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,1)).toString());
					System.out.println(String.valueOf(total).toString());
					System.out.println(String.valueOf(inicio[indice]).toString());
					mediana.setText("MEDIANA: "+String.valueOf(inicio[indice]+total).toString());
				}
				indice++;
			}
		}
	}
/******************************************Desviacion*************************************************/
	public void buscarDesviacion(float prom){
		double suma=(double)0;
		double potencia=(double)0;
		double multiplicacion=(double)0;
		int acum=0,fin=0;
		double estandar=0;
		double aux=0;
		int indice=0;
		String dato,valor;
		if(modo.compareTo("s")==0){
			desviacion.setText("DESVIACION: ");
			coeficiente.setText("COEFICIENTE: ");
			fin=buscarFin();
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
					dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
					valor=String.valueOf(jtabla.getValueAt(indice,0)).toString();
					potencia=(int)Math.pow((double)(Float.parseFloat(valor)-prom),(double)2);
					multiplicacion=(double)(potencia*(Integer.parseInt(dato)));
					jtabla.setValueAt(multiplicacion,indice,5);
					suma+=multiplicacion;
				indice++;
			}
			dato=String.valueOf(jtabla.getValueAt(fin-1,2)).toString();
			acum=Integer.parseInt(dato);
			estandar=Math.sqrt((double)(suma/acum));
			estandar=(Math.floor(estandar*10000))/10000;
			//System.out.println(String.valueOf(estandar).toString());
			desviacion.setText("DESVIACION: "+String.valueOf(estandar).toString());
			aux=(estandar/prom)*100;
			aux=(Math.floor(aux*100))/100;
			coeficiente.setText(coeficiente.getText()+String.valueOf(aux).toString()+"%");
		}else{

		}
	}
/******************************************Promedio***************************************************/
	public void buscarPromedio(){
		int fin=0;
		float suma=(float)0;
		int indice=0;
		if(modo.compareTo("s")==0){
			promedio.setText("PROMEDIO: ");
			fin=Integer.parseInt(String.valueOf(jtabla.getValueAt(buscarFin()-1,2)).toString());
				while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
					suma+=((Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,0)).toString()))*(Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString())));
					indice++;
				}
				suma/=fin;
				promedio.setText(promedio.getText()+String.valueOf(suma).toString());
				buscarDesviacion(suma);
		}else{
			promedio.setText("PROMEDIO: ");
			fin=Integer.parseInt(String.valueOf(jtabla.getValueAt(buscarFin()-1,2)).toString());
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				suma+=((Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,5)).toString()))*(Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString())));
				indice++;
			}
			suma/=fin;
			promedio.setText(promedio.getText()+String.valueOf(suma).toString());
			//buscarDesviacion(suma);
		}
	}
/******************************************MODA*******************************************************/
	public void buscarModa(){
		float []mod=new float[2];
		float []aux=new float[2];
		int indice=0;
		if(modo.compareTo("s")==0){
			moda.setText("MODA: ");
			mod[0]=Float.parseFloat(String.valueOf(jtabla.getValueAt(0,0)).toString());
			mod[1]=Float.parseFloat(String.valueOf(jtabla.getValueAt(0,1)).toString());

			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				aux[0]=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,0)).toString());
				aux[1]=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,1)).toString());
				if(mod[1]<aux[1]){
					mod[0]=aux[0];
					mod[1]=aux[1];
				}
				indice++;
			}
			moda.setText(moda.getText()+String.valueOf(mod[0]).toString());
		}else{
			moda.setText("MODA: ");
						mod[0]=Float.parseFloat(String.valueOf(jtabla.getValueAt(0,5)).toString());
						mod[1]=Float.parseFloat(String.valueOf(jtabla.getValueAt(0,1)).toString());

						while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
							aux[0]=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,5)).toString());
							aux[1]=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,1)).toString());
							if(mod[1]<aux[1]){
								mod[0]=aux[0];
								mod[1]=aux[1];
							}
							indice++;
						}
			moda.setText(moda.getText()+String.valueOf(mod[0]).toString());
		}
	}
/******************************************Actualizacion**********************************************/
	public void actualizarFrecuencias(){
		int indice=0,acumulada=0,cantidad=0;
		float fr=(float)0,fri=(float)0;
		int fin=0,total=0;
		String dato;
		if(modo.compareTo("s")==0){
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
				indice++;
			}
			fin=buscarFin();
			indice=0;
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){

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
		}else{
			float []v=new float[100];
			int []f=new int [100];
			float amplitud=(float)0;
			float intervalo=(float)0;
			int t=0;

			indice=0;
			t=buscarFin();
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				dato=String.valueOf(jtabla.getValueAt(indice,0)).toString();
				v[indice]=Float.parseFloat(dato);
				dato=String.valueOf(jtabla.getValueAt(indice,1)).toString();
				f[indice]=Integer.parseInt(dato);
				indice++;
			}
			dato=String.valueOf(jtabla.getValueAt(t-1,0)).toString();
			amplitud=Float.parseFloat(dato);
			dato=String.valueOf(jtabla.getValueAt(0,0)).toString();
			amplitud=(amplitud-Float.parseFloat(dato))+1;
			intervalo=Float.parseFloat(cantintervalo.getText());
			//System.out.println(String.valueOf(amplitud).toString());
			amplitud/=intervalo;

			inicio[0]=Float.parseFloat(String.valueOf(jtabla.getValueAt(0,0)).toString());
			fina[0]=inicio[0]+amplitud;

			for(int x=1; x<t;x++){
				inicio[x]=fina[x-1];
				fina[x]=inicio[x]+amplitud;
			}
			remove(scrtabla);
			jtabla=new JTable(100,6);
			scrtabla=new JScrollPane(jtabla);
			scrtabla.setBounds(200,30,350,270);
			add(scrtabla);
			for(int x=0;x<intervalo;x++) {
				jtabla.setValueAt("["+String.valueOf(inicio[x]).toString()+";"+String.valueOf(fina[x]).toString()+")",x,0);
				jtabla.setValueAt(0,x,1);
			}
			for(indice=0;indice<t;indice++){
				for(int x=0;x<intervalo;x++){
					if((v[indice]>=inicio[x]) && (v[indice]<fina[x])){
						dato=String.valueOf(jtabla.getValueAt(x,1)).toString();
						jtabla.setValueAt(Integer.parseInt(dato)+f[indice],x,1);
					}
				}
			}
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
				indice++;
			}
			fin=buscarFin();
			indice=0;
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){

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


			etiqueta.setText("x:               fi:               Fi:              fri:             Fri:    Marca Clase:");
			etiqueta.setVisible(true);
			etiqueta.setBounds(225,13,350,20);
			add(etiqueta);
			indice=0;
			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){

				//System.out.println(String.valueOf(fin).toString());
				jtabla.setValueAt((inicio[indice]+(amplitud/2)),indice,5);
				indice++;
			}

			buscarModa();
			buscarPromedio();
			buscarMediana();
		}
	}
/******************************************ordena Vector**********************************************/
	public void ordenarVector(Vector v, Vector f){
		int x,y;
		float num=(float)0,num1=(float)0;
		int a=0,b=0;
		String valor,valor1;
		if(modo.compareTo("s")==0){
			for(x=0;x<v.size();x++){
				for(y=0;y<v.size();y++){
					valor=String.valueOf(v.elementAt(x)).toString();
					valor1=String.valueOf(v.elementAt(y)).toString();
					num=Float.parseFloat(valor);
					num1=Float.parseFloat(valor1);
					if(num>num1){
						v.setElementAt(num,y);
						v.setElementAt(num1,x);

						valor=String.valueOf(f.elementAt(x)).toString();
						valor1=String.valueOf(f.elementAt(y)).toString();
						a=Integer.parseInt(valor);
						b=Integer.parseInt(valor1);

						f.setElementAt(a,y);
						f.setElementAt(b,x);

					}
				}
			}
		}
	}
/******************************************mostrar GRAFICO********************************************/
	public void mostrarGrafico(){
		float []v;
		int []f;
		int indice=0;
		int x,y;
		float num=(float)0;
		int num1=0;
		if(modo.compareTo("s")==0){
			v=new float[100];
			f=new int[100];

			while((jtabla.getValueAt(indice,0)!=null) && (jtabla.getValueAt(indice,0)!="")){
				v[indice]=Float.parseFloat(String.valueOf(jtabla.getValueAt(indice,0)).toString());
				f[indice]=Integer.parseInt(String.valueOf(jtabla.getValueAt(indice,1)).toString());
				indice++;
			}

			//System.out.println(String.valueOf(indice).toString());
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
	}
/******************************************Guardar Datos**********************************************/
	public void guardarDatos(){
		JOptionPane aviso=new JOptionPane();
		int indice=0,columna=0;
		if(modo.compareTo("s")==0){
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
	}
/***************************************leer DATOS****************************************************/
	public void leerDatos(){
		JOptionPane aviso=new JOptionPane();
		int indice=0,columna=0,contador=0;

		vector.clear();

		remove(scrtabla);
		jtabla=new JTable(100,6);
		scrtabla=new JScrollPane(jtabla);
		scrtabla.setBounds(200,30,350,270);
		add(scrtabla);
		etiqueta.setText("x:               fi:               Fi:              fri:             Fri:    (x-prom)^2 fi:");
		etiqueta.setBounds(225,13,350,20);
		add(etiqueta);

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
			moda.setText("MODA: ");
			promedio.setText("PROMEDIO: ");
			mediana.setText("MEDIANA: ");
        }
	}
/******************************************actionPERFORMED********************************************/
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==analizar){
			txtcarga.setText("");
			txtcarga.setEnabled(false);
			ordenarTabla();
			actualizarFrecuencias();
			buscarModa();
			buscarPromedio();
			buscarMediana();

		}else{
			if(ae.getSource()==btngrafico){
				mostrarGrafico();
			}else{
				if(ae.getSource()==btnguarda){
					guardarDatos();
					txtcarga.setEnabled(true);
				}else{
					if(ae.getSource()==btnleer){
						btnguarda.setEnabled(true);
						simple.setEnabled(true);
						leerDatos();
					}else{
						if(ae.getSource()==nuevo){
							txtcarga.setEnabled(true);
							cantintervalo.setVisible(false);
							cargarintervalo.setVisible(false);
							remove(scrtabla);
							jtabla=new JTable(100,6);
							scrtabla=new JScrollPane(jtabla);
							scrtabla.setBounds(200,30,350,270);
							add(scrtabla);
							etiqueta.setText("x:               fi:               Fi:              fri:             Fri:    (x-prom)^2 fi:");
							etiqueta.setBounds(225,13,350,20);
							add(etiqueta);
							modo="s";
							btnguarda.setEnabled(true);
							simple.setEnabled(true);
						}else{
							if(ae.getSource()==simple){
								cantintervalo.setVisible(false);
								cargarintervalo.setVisible(false);
								modo="s";
								actualizarFrecuencias();

							}else{
								if(ae.getSource()==intervalo){
									cantintervalo.setVisible(true);
									cargarintervalo.setVisible(true);
									modo="i";
									btnguarda.setEnabled(false);
									simple.setEnabled(false);
								}else{
									if(ae.getSource()==cargarintervalo){
										actualizarFrecuencias();
									}
								}
							}
						}
					}
				}
			}
		}
	}
/******************************************KEY LISTENER***********************************************/
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			cargarDato();
			txtcarga.setText("");
		}
	}
/******************************************MAIN*******************************************************/
	public static void main(String []s){
		Algoritmos algo=new Algoritmos();
		algo.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}