package org.acme.service;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.ConfigProvider;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@ApplicationScoped
public class SeaweedFSDevService {

    private static GenericContainer<?> container;

    void iniciar(@Observes StartupEvent event) {
        boolean enabled = ConfigProvider.getConfig()
                .getOptionalValue("seaweedfs.devservice.enabled", Boolean.class)
                .orElse(false);

        if (!enabled) {
            return;
        }

        if (container != null && container.isRunning()) {
            return;
        }

        container = new GenericContainer<>(DockerImageName.parse("chrislusf/seaweedfs:3.85"))
                .withExposedPorts(9333, 8080)
                .withCommand(
                        "server",
                        "-master.port=9333",
                        "-volume.port=8080",
                        "-dir=/data"
                )
                .waitingFor(Wait.forListeningPorts(9333, 8080));

        container.start();

        String masterUrl = "http://" + container.getHost() + ":" + container.getMappedPort(9333);
        String volumeUrl = "http://" + container.getHost() + ":" + container.getMappedPort(8080);

        System.setProperty("seaweedfs.master.url", masterUrl);
        System.setProperty("seaweedfs.volume.url", volumeUrl);
    }

    void parar(@Observes ShutdownEvent event) {
        if (container != null) {
            container.stop();
            container = null;
        }
    }
}