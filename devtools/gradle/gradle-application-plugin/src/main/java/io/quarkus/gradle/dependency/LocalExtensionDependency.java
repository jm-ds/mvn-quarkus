package io.quarkus.gradle.dependency;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;

import io.quarkus.bootstrap.model.AppArtifactCoords;
import io.quarkus.maven.dependency.ArtifactKey;

public class LocalExtensionDependency extends ExtensionDependency {

    private static final String DEFAULT_DEPLOYMENT_PATH_SUFFIX = "deployment";

    private Project localProject;

    public LocalExtensionDependency(Project localProject, ModuleVersionIdentifier extensionId,
            AppArtifactCoords deploymentModule,
            List<Dependency> conditionalDependencies, List<ArtifactKey> dependencyConditions) {
        super(extensionId, deploymentModule, conditionalDependencies, dependencyConditions);
        this.localProject = localProject;
    }

    public String findDeploymentModulePath() {

        String deploymentModuleName = DEFAULT_DEPLOYMENT_PATH_SUFFIX;
        if (localProject.getParent().findProject(deploymentModule.getArtifactId()) != null) {
            deploymentModuleName = deploymentModule.getArtifactId();
        }

        String parentPath = localProject.getParent().getPath();
        if (parentPath.endsWith(":")) {
            return parentPath + deploymentModuleName;
        }

        return parentPath + ":" + deploymentModuleName;
    }
}
