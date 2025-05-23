package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mocha.util.listener.CustomEvent;
import org.mocha.util.listener.CustomListener;

import lombok.Getter;
import lombok.Setter;

public class CustomListenerTest {
    private CustomListener<Trigger> listener = new CustomListener<>();

    @Test
    public void eventAttributesEditing() {
        listener.clear();

        listener.addListener(Trigger.ATTACK, 0, e -> {
            var ae = (AttackEvent) e;
            ae.setDamage(ae.getDamage() * 2);
        });

        var ae = listener.dispatch(new AttackEvent(2));
        assertEquals(4, ae.damage);
    }

    @Test
    public void priority() {
        listener.clear();

        listener.addListener(Trigger.ATTACK, 0, e -> {
            var ae = (AttackEvent) e;
            ae.setDamage(ae.getDamage() * 2);
        });

        listener.addListener(Trigger.ATTACK, 1, e -> {
            var ae = (AttackEvent) e;
            ae.setDamage(ae.getDamage() - 1);
        });

        var ae = listener.dispatch(new AttackEvent(2));
        assertEquals(3, ae.damage);
    }

    @Getter
    @Setter
    private class AttackEvent extends CustomEvent<Trigger> {
        private int damage;

        public AttackEvent(int damage) {
            super(Trigger.ATTACK);
            this.damage = damage;
        }
    }

    private enum Trigger {
        ATTACK;
    }
}
