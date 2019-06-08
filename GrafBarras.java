import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.*;

public class GrafBarras extends JPanel{
    boolean setValue = false;
    float [] num;
    int [] frec;
    public GrafBarras(){
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
        int escala = 0,x,cantidad=0;

        double punto=0;

        if (setValue){

            for(x=0;x<frec.length;x++){
				if(maximo<frec[x]){
					maximo=frec[x];
				}
				if(frec[x]!=0){
					cantidad++;
				}
			}
			int [] frecuencia= new int [cantidad];

			cantidad=0;
			for(x=0;x<frec.length;x++){
				if(frec[x]!=0){
					frecuencia[cantidad]=frec[x];
					cantidad++;
				}
			}
			maximo+=1;

            //System.out.println(maximo);

            escala = cantidad+1;
            g2.setColor(Color.black);
            for(punto=0;punto<escala;punto++){
                g2.draw(new Line2D.Double(punto*getWidth()/escala, getHeight(),punto*getWidth()/escala,getHeight()-5 ));
            }
            escala= maximo;
            for(punto=0;punto<escala;punto++){
                g2.draw(new Line2D.Double(0, punto*getHeight()/escala,6, punto*getHeight()/escala));
            }
            int numero=0;
            escala = cantidad+1;
            for(punto=1;punto<cantidad+1;punto++){
                g2.draw(new Line2D.Double(punto*getWidth()/escala, getHeight(),punto*getWidth()/escala,getHeight()-(frecuencia[numero]*getHeight()/maximo) ));
                numero++;
            }

        }


    }


}