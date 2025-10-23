package iticbcn.xifratge;

public class TextXifrat {
    
    byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return new String(bytes);
    }
    public byte[] getBytes() {
        return this.bytes;
    }
}
