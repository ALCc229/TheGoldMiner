import java.awt.*;

public class Object {
    int x;
    int y;

    int width;
    int height;

    Image img;

    Boolean flag;
    //标记是否能移动

    int m;//质量

    //积分
    int count;

    //类型 1金块 2石块
    int type;

    void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
