package iticbcn.xifratge;

public class TextXifrat {
    
    byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return this.bytes.toString();
    }
    public byte[] getBytes() {
        return this.bytes;
    }
}
