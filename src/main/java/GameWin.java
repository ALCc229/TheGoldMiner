import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class GameWin extends JFrame {

    //游戏状态 0未开始 1运行中 2商店 3失败 4胜利
    static int state;


    //存储金块
    List<Object> objectList = new ArrayList<>();

    Bg bg = new Bg();
    Line line = new Line(this);
    Image offScreenImage;

    {
        for (int i = 0; i < 11; i++) {
            boolean isPlace = true;
            double random = Math.random();
            Gold gold;
            if(random < 0.3 ){
                gold = new GoldMini();
            } else if (random < 0.7) {
                gold = new Gold();
            }else {
                gold = new GoldPlus() ;
            }

            for (Object object : objectList) {
                if (gold.getRec().intersects(object.getRec())){
                    isPlace = false;
                }
            }
            if(isPlace){
                objectList.add(gold);
            }else {
                isPlace = true;
                i--;
            }

        }
        for (int i = 0; i < 5; i++) {
            boolean isPlace = true;
            Rock rock = new Rock();
            for (Object object : objectList) {
                if(rock.getRec().intersects(object.getRec())){
                    isPlace=false;
                }
            }
            if (isPlace){
                objectList.add(rock);
            }else {
                isPlace = true;
                i--;
            }
        }
    }

    void launch() throws InterruptedException {
        this.setVisible(true);
        this.setSize(768,1000);
        this.setLocationRelativeTo(null);
        this.setTitle("十六號黄金矿工");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        if(e.getButton() == 3){
                            state = 1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        if(e.getButton() == 1 && line.state == 0){
                            line.state = 1;
                        }
                        if (e.getButton() == 3&& line.state ==3 ){
                            Bg.waterFlag = true;
                            Bg.waterNum--;

                            //设置药水数量为0时不可使用功能
                            if (Bg.waterNum < 0){
                                Bg.waterNum ++;
                                Bg.waterFlag = false;
                            }
                        }
                        break;
                    case 2:break;
                    case 3:break;
                    case 4:break;
                    default:
                }



            }
        });


        while (true){
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void nextLevel(){

        if(bg.gameTime() && state == 1){
            if(Bg.count >= bg.goal){
                if(Bg.level == 5){
                    state  = 4;
                }else {
                    Bg.level++;
                }

                dispose();
                GameWin gameWin1 = new GameWin();
                try {
                    gameWin1.launch();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else {
                state = 3;
            }
        }

    }


    @Override
    public void paint(Graphics g) {
        offScreenImage = this.createImage(768,1000);
        Graphics gImage = offScreenImage.getGraphics();

        bg.painSelf(gImage);

        if(state == 1){
            for (Object object : objectList) {
                object.paintSelf(gImage);
            }

            line.paintSelf(gImage);
        }
        g.drawImage(offScreenImage,0,0,null);
    }

    public static void main(String[] args) throws InterruptedException {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }

}
