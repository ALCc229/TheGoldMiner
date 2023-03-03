import java.awt.*;

public class Line {
    int x = 380;
    int y = 180;
    int endx = 500;
    int endy = 500;

    double length = 100;
    double miniLength = 100;
    double maxLength = 750;
    double n = 0;

    int dir = 1;//  0摇摆  1抓取 2收回  3抓取返回

    int state;

    //钩爪图片
    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");

    GameWin frame;

    Line(GameWin frame){
        this.frame = frame;
    }

    void logic(){

        for (Object object : this.frame.objectList) {
            if(endx > object.x && endx < object.x + object.width
                    && endy > object.y && endy < object.y + object.height){
                state = 3;
                object.flag = true;
            }
        }



    }

    void lines(Graphics g){
        endx = (int) (x + length*Math.cos(n*Math.PI));
        endy = (int) (y + length*Math.sin(n*Math.PI));

        g.setColor(Color.red);
        g.drawLine(x-1,y,endx-1 ,endy);
        g.drawLine(x,y,endx ,endy);
        g.drawLine(x+1,y,endx+1,endy);
        g.drawImage(hook,endx-36,endy-2,null);
    }



    void paintSelf(Graphics g){

        logic();
        switch (state){
            case 0:
                if(n<0){
                    dir = 1;
                }else if( n>1){
                    dir = -1;
                }

                n +=0.005 * dir;

                lines(g);
                break;
            case 1:
                if (length<maxLength) {
                    length =length+5;
                    lines(g);
                }else {
                    state = 2 ;
                }
                break;

            case 2:
                if (length>miniLength) {
                    length = length - 5;
                    lines(g);
                }else {
                    state = 0;
                }
                break;

            case 3:
                int m = 1;
                if(length>miniLength) {
                    length = length - 5;
                    lines(g);
                    for (Object object : this.frame.objectList) {
                        if (object.flag){
                            m = object.m;
                            object.x = endx -object.getWidth()/2;
                            object.y = endy;
                            if(length<=miniLength){
                                object.y = -150;
                                object.x = -150;
                                object.flag = false;
                                Bg.waterFlag  = false;
                                state = 0;
                                Bg.count += object.count;
                            }
                            if(Bg.waterFlag){
                                if(object.type ==1){
                                    m = 1;
                                }
                                if (object.type == 2){
                                    object.y = -150;
                                    object.x = -150;
                                    object.flag = false;
                                    Bg.waterFlag  = false;
                                    state = 2;
                                }
                            }
                        }

                    }
                }
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
        }


    }
}
