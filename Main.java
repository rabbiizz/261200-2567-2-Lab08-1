import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // threads จะพิมพ์ข้อความโดยไม่ชะลอเวลา เวลา run ออกมาแล้วแต่ละตัวจะขึ้นอยู่กับเวลาของแต่ละ thread จาก OS
        // ผลลัพธ์จะแสดงให้เห็นว่า thread 1 2 3 อาจจะมาในลำดับไหนก็ได้

        NumberPrinter t1 = new NumberPrinter(1);
        NumberPrinter t2 = new NumberPrinter(2);
        NumberPrinter t3 = new NumberPrinter(3);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // เมื่อเราเพิ่ม Thread.sleep() ภายในฟังก์ชัน run ของแต่ละ thread การพิมพ์แต่ละหมายเลขจะชะลอเวลาแบบสุ่ม
        // โดยแต่ละ thread จะ sleep ระหว่าง 10 ถึง 100 มิลลิวินาทีหลังจากการพิมพ์แต่ละเลข เวลา run ออกมาจะได้ผลลัพธ์ที่หลากหลายมากขึ้นแต่จะไม่เรียงลำดับ
        // บางที thread หนึ่งอาจจะพิมพ์หลายหมายเลขติดกัน ในขณะที่อีก thread หนึ่งอาจจะพิมพ์น้อยกว่า

    }
}

class NumberPrinter extends Thread {
    private int threadNumber;

    public NumberPrinter(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 1; i <= 50; i++) {
            try {
                Thread.sleep(random.nextInt(91) + 10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("thread #" + threadNumber + " : " + i);
        }
    }
}
//การทำงานของ multithaerding คือสร้างเธรดผ่านการสืบทอด class Thaerd หรือใช้ Runable interface
//รันเธรด เรียกใช้ method start() เพื่อเริ่มการทำงานของเธรด ซึ่งจะไปเรียก run() ในเบื้องหลัง
//รันแบบขนาน เธรดแต่ละตัวทำงานอิสระจากกัน โดยระบบปฏิบัติการจะจัดการการสลับการทำงาน (context switching) ระหว่างเธรด
//เหตุผลที่ลำดับ output ถึงเปลี่ยนแปลงระหว่างการรันคือ ไม่กำหนดลำดับการทำงาน การจัดการลำดับเธรดขึ้นอยู่กับ Thread Scheduler ของ JVM ซึ่งอาจแตกต่างกันในแต่ละครั้งที่รัน
//การสลับเธรด เธรดต่าง ๆ อาจถูกสลับการทำงานในเวลาที่แตกต่างกัน ทำให้ลำดับการแสดงผลไม่แน่นอน