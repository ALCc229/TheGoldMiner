import java.awt.*;

public class Bg {

    //总分
    static int count = 0;

    static int waterNum = 3;
    static boolean waterFlag = false;

    //关卡数
    static int level = 1;
    //目标得分
    int goal  = level * 15 + count;

    //开始时间
    long startTime;
    long endTime;
    //结束时间

    Image bg = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
    Image bg1 = Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
    Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
    Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");

    void painSelf(Graphics g){
        g.drawImage(bg1,0,0,null);
        g.drawImage(bg,0,200,null);

        switch(GameWin.state){
            case 0:
                drawWord(g,Color.black,80,"准备开始",200,400);
                break;
            case 1:
                g.drawImage(peo,310,50,null);
                //当前积分
                drawWord(g,Color.black,30,"积分:" + count,30,150);
                g.drawImage(water,450,40,null);
                //药水数量
                drawWord(g,Color.black,30,"*" + waterNum,510,70);
                //关卡数
                drawWord(g,Color.black,30,"第" + level + "关",30,60);
                //目标积分
                drawWord(g,Color.black,30,"目标" + goal,30,110);
                //时间计时
                endTime = System.currentTimeMillis();
                long sumTime = 45- (endTime - startTime) / 1000;
                drawWord(g,Color.black,30,"时间:" + (sumTime > 0?sumTime : 0),520,150);
                break;
            case 2:break;
            case 3:
                drawWord(g,Color.black,80,"游戏失败" ,250,350);
                drawWord(g,Color.black,80,"积分" + count,20,450);
                break;
            case 4:
                drawWord(g,Color.red,80,"游戏成功" ,250,350);
                drawWord(g,Color.red,80,"积分" + count,20,450);
                break;
            default:
        }
    }

    boolean gameTime(){
        long time = (endTime - startTime) / 1000;
        if(time > 45){
            return true;
        }else {
            return false;
        }
    }

    public static void drawWord(Graphics g,Color color,int size,String str, int x, int y){
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
