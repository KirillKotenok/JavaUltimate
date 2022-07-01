package web.socket.ssl;

public class Demo {
    public static void main(String[] args) {
        var largestPicture = PictureUtils.getLargestPicture();
        System.out.printf("The largest picture based on url: %s \nWith size: %s",
                largestPicture.getUrl(),
                largestPicture.getSize());
    }
}
