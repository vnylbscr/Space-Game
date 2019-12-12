
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates
{
    private int x;
    private int y;
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
}

public class Oyun extends JPanel implements KeyListener,ActionListener
{
    Timer timer=new Timer(5, this);
    long startTime=System.currentTimeMillis();
    private long gecenSure=0;
    
    private int harcananAtes=0;
    int dialogButton = JOptionPane.YES_NO_OPTION;
    
    private BufferedImage image;
    
    ArrayList<Ates> atesler=new ArrayList<Ates>();
    
    private int atesdirY=1;
    
    private int topX=0;
    
    private int topdirX=2;
    
    private int uzayGemisiX=0;
    
    private int uzayDirX=20;

    public boolean kontrol(){
        if (atesler.stream().anyMatch((ates) -> (new Rectangle(ates.getX(),ates.getY(),12,20).intersects(new Rectangle(topX,0,20,20))))) 
        {
            return true;
        }
        return false;
    }
            
    public Oyun() throws FileNotFoundException 
    {
        try {
            image=ImageIO.read(new FileInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setOpaque(true);
        
        setBackground(Color.BLACK);
        
        timer.start();
        
    }
    
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        
        
        g.setColor(Color.RED);
        
        g.fillOval(topX, 0, 40, 40);
        
        g.drawImage(image, uzayGemisiX, 490,image.getWidth()/10,image.getHeight()/10, this);
        
        atesler.stream().filter((ates) -> (ates.getY()<0)).forEach((ates) -> {
            atesler.remove(ates);
        });
 
        g.setColor(Color.BLUE);
        
        atesler.stream().forEach((ates) -> {
            g.fillRect(ates.getX(), ates.getY(), 12, 20);
        });
        
        if (kontrol()) {
            
            timer.stop();
            long endTime=System.currentTimeMillis();
            
            gecenSure=endTime-startTime;
            
            double saniye=(double)(gecenSure/1000);
            
            String mesaj="Harcanan Ateş:\t"+harcananAtes+"\n"+"Geçen Süre:\t"+gecenSure+"\t";
            
            JOptionPane.showMessageDialog(this, mesaj);
            
           /* JOptionPane.showConfirmDialog(null, "Yenden oynamak istiyor musun?");
            if (dialogButton==JOptionPane.YES_OPTION) {
                
                try {
                    restartApplication();
                } catch (IOException ex) {
                    Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            */
            
        }
        
        
    }

    @Override
    public void repaint() 
    {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    
    public void keyTyped(KeyEvent e) 
    {
        
        
    }
    

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int tus=e.getKeyCode();
        
        if(tus==KeyEvent.VK_LEFT)
        {
            if(uzayGemisiX<=0) 
            {
                uzayGemisiX=0;
            }
            else
            {
                uzayGemisiX-=uzayDirX;
            }
                
        }
        else if (tus==KeyEvent.VK_RIGHT) {
            
            if (uzayGemisiX>=750) {
                
                uzayGemisiX=750;
            }
            else {
                uzayGemisiX+=uzayDirX;
            }     
        }
        
        else if(tus==KeyEvent.VK_SPACE)
        {
            atesler.add(new Ates(uzayGemisiX+15, 470));
            
            harcananAtes++;
        }
//        else if (tus==KeyEvent.VK_RIGHT && tus==KeyEvent.VK_SPACE) {
//            
//            atesler.add(new Ates(uzayGemisiX+15, 470));
//            
//            harcananAtes++;
//        }
//        else if (tus==KeyEvent.VK_LEFT && tus==KeyEvent.VK_SPACE) {
//            
//            atesler.add(new Ates(uzayGemisiX+15, 470));
//            
//            harcananAtes++;
//        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        
    }

    @Override
    
    public void actionPerformed(ActionEvent e) 
    {
        atesler.stream().forEach(new Consumer<Ates>() {

            public void accept(Ates ates) {
                ates.setY(ates.getY()-atesdirY);
            }
        });
        topX += topdirX;
        
        if (topX>750) 
        {
         topdirX=-topdirX;
        }
        if (topX<=0) {
           topdirX=-topdirX;
        }
     
        repaint();
    }
 
    
    
    
}
