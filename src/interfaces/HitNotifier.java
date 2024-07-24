package interfaces;

public interface HitNotifier {
    void addHitListener(HitListener hl);
    void removeHitListener(HitListener hl);
}
