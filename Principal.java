import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Principal extends javax.swing.JFrame implements ActionListener {
    GrafBarras gf;
    //GrafTorta gf;
    JButton jbtnGraficar;

    public Principal() {
		jbtnGraficar = new JButton("Graficar");

        gf = new GrafBarras ();
        setLayout(new BorderLayout());

        add("Center",gf);
        add("South",jbtnGraficar);

        jbtnGraficar.addActionListener(this);
    }

	public void actionPerformed (ActionEvent ae){
		if (ae.getSource()==jbtnGraficar){
			int [] n = new int[3];
			int [] f = new int[3];
			n[0]=1;
			n[1]=2;
			n[2]=3;
			f[0]=1;
			f[1]=5;
			f[2]=6;
			gf.setValues(n,f);
			gf.repaint();
		}
}


    public static void main(String args[]) {
		Principal c = new Principal();
		c.setTitle("Contactos");
		c.setSize (500,500);
		c.setVisible(true);
		c.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}