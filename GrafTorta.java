import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.*;

public class GrafTorta extends JPanel{
    boolean setValue = false;
    float [] num;
    int [] frec;
    public GrafTorta(){
            setMaximumSize(new Dimension (500,500));
            setMinimumSize(new Dimension (500,500));
            setPreferredSize(new Dimension (500,500));
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.black));
            setBackground(Color.white);
    }

    public void setValues(float [] n,int [] f){
        num=n;
        frec=f;
        setValue=true;
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int maximo=0;
        int escala,suma=0,x;

        double punto=0,pi=4*(Math.atan(1)),ai,af=0,porcentaje;

        if (setValue){
            maximo = frec[frec.length-1];
			for(x=0;x<frec.length;x++){
				suma+=frec[x];
			}
			ai=0;
			//g2.setColor(Color.green);
			for(x=0;x<frec.length;x++){

				porcentaje=(double)frec[x]*100/suma;
				af=(double)af+ porcentaje*360/100;

				//System.out.println(ai + "  " + af + "  " + porcentaje );

				g2.draw(new Arc2D.Double(getHeight()/2-100 ,getWidth()/2-100,200,200,0,af,Arc2D.PIE));


			}
        }


    }


}