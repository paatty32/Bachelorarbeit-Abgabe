package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw;

import com.vaadin.flow.shared.Registration;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<List<Group>>> trainingGroupListeners = new LinkedList<>();

    static LinkedList<Consumer<List<Group>>> ownTrainingGroupListener = new LinkedList<>();

    static LinkedList<Consumer<List<GroupRequest>>> groupRequestlisteners = new LinkedList<>();


    public static synchronized Registration register(
            Consumer<List<Group>> listener) {
        trainingGroupListeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                trainingGroupListeners.remove(listener);
            }
        };
    }

    public static synchronized Registration registerGroupRequestListener(
            Consumer<List<GroupRequest>> listener) {
        groupRequestlisteners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                groupRequestlisteners.remove(listener);
            }
        };
    }

    public static synchronized Registration registerOwnTrainingGroupListener(
            Consumer<List<Group>> listener) {
        ownTrainingGroupListener.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                ownTrainingGroupListener.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(List<Group> message) {
        for (Consumer<List<Group>> listener : trainingGroupListeners) {
            executor.execute(() -> listener.accept(message));
        }
    }

    public static synchronized void broadcastGroupRequestListener(List<GroupRequest> message) {
        for (Consumer<List<GroupRequest>> listener : groupRequestlisteners) {
            executor.execute(() -> listener.accept(message));
        }
    }

    public static synchronized void broadCastOwnTrainingGroupListener(List<Group> message) {
        for (Consumer<List<Group>> listener : ownTrainingGroupListener) {
            executor.execute(() -> listener.accept(message));
        }
    }
}