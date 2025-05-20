package org.mocha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class SceneManager {
    private Scene actualScene;
    private List<Scene> scenes;

    public SceneManager() {
        actualScene = new Scene("Scene");
        scenes = new ArrayList<>();
        actualScene.setActive(true);
        scenes.add(actualScene);
    }

    public SceneManager(Scene actualScene) {
        this.actualScene = actualScene;
        scenes = new ArrayList<>();
        actualScene.setActive(true);
        scenes.add(actualScene);
    }

    public void addScene(Scene scene) {
        scenes.add(actualScene);
    }

    public void removeScene(Scene scene) {
        scenes.remove(actualScene);
    }

    public void changeToScene(String sceneName) {
        Scene sceneTo = null;

        for (Scene s : scenes) {
            if (s.getName().equals(sceneName)) {
                sceneTo = s;
                break;
            }
        }

        actualScene.setActive(false);
        actualScene = sceneTo;
        actualScene.setActive(true);
        actualScene.start();
    }
}
