package pharmacy;

import javax.swing.*;
import java.awt.*;

public class Marker extends JComponent {

	private double distance; // distance 변수를 double 타입으로 변경
	private Image markerIcon; // 아이콘 이미지를 저장할 변수 추가

    // 생성자 초기 거리는 0.0으로 설정
    public Marker() {
        this.distance = 0.0;
        // 아이콘 이미지 로드
        this.markerIcon = new ImageIcon("images/location4.png").getImage();
    }

    // 거리 업데이트 메서드
    public void updateDistance(double distance) {
        this.distance = distance * 7;
        repaint(); // 거리가 업데이트될 때마다 다시 그리기
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (int) distance; // x 좌표를 거리로 설정
        int y = getHeight() / 2; // y 좌표를 컴포넌트 높이의 중간으로 설정
 
        g.drawImage(markerIcon, x, y, this);
    }
}
