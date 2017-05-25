package bg.sofia.uni.fmi.sda.frontbookkeeper;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private List<Integer> soldiers;
    private List<Unit> attachedUnits;
    private Unit attachedTo;

    public Unit() {
        attachedTo = null;
        soldiers = new ArrayList<>();
        attachedUnits = new ArrayList<>();
    }

    public Unit getAttachedTo() {
        return attachedTo;
    }

    public void setAttachedTo(Unit attachedTo) {
        this.attachedTo = attachedTo;
    }

    public List<Integer> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<Integer> soldiers) {
        this.soldiers = soldiers;
    }

    public List<Unit> getAttachedUnits() {
        return attachedUnits;
    }

    public void setAttachedUnits(List<Unit> attachedUnits) {
        this.attachedUnits = attachedUnits;
    }

    public void addSoldier(int soldier) {
        soldiers.add(soldier);
    }

    public void addUnit(Unit unit) {

        if (unit.getAttachedTo() != null) {

            for (int i = 0; i < unit.getSoldiers().size(); i++) {
                if (unit.getAttachedTo().getSoldiers().contains(unit.getSoldiers().get(i))) {
                    unit.getAttachedTo().getSoldiers().remove(unit.getSoldiers().get(i));
                }
            }
        }
        soldiers.addAll(unit.getSoldiers());
        unit.setAttachedTo(this);
        attachedUnits.add(unit);
    }

    public void addSoldiersAfterSoldier(int soldier, Unit unit) {
        if (unit.getAttachedTo() != null) {

            for (int i = 0; i < unit.getSoldiers().size(); i++) {
                if (unit.getAttachedTo().getSoldiers().contains(unit.getSoldiers().get(i))) {
                    unit.getAttachedTo().getSoldiers().remove(unit.getSoldiers().get(i));
                }
            }
        }
        soldiers.addAll(soldiers.indexOf(soldier) + 1, unit.getSoldiers());
        unit.setAttachedTo(this);
        attachedUnits.add(unit);

    }

    public void removeSoldiers(ArrayList<Integer> s, Unit unit) {

        for (int i = 0; i < s.size(); i++) {
            if (unit.getSoldiers().contains(s.get(i))) {
                unit.getSoldiers().remove(s.get(i));
            }
        }

    }

    public void soldiersDied(int start, int end) {
        int startIndex = soldiers.indexOf(start);
        int endIndex = soldiers.indexOf(end);

        if (startIndex != -1 && endIndex != -1) {
            ArrayList<Integer> deadSoldiers = new ArrayList<>();
            for (int i = startIndex; i <= endIndex; i++) {
                deadSoldiers.add(soldiers.get(i));
            }
            removeSoldiers(deadSoldiers, this);
            if (attachedUnits.size() > 0) {
                for (Unit u : attachedUnits) {
                    removeSoldiers(deadSoldiers, u);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (soldiers.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[");
            for (int i = 0; i < soldiers.size() - 1; i++) {
                sb.append(soldiers.get(i));
                sb.append(", ");
            }
            sb.append(soldiers.get(soldiers.size() - 1));
            sb.append("]");
        }
        return sb.toString();
    }
}
