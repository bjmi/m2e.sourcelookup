package bjmi.m2e.sourcelookup;

import bjmi.m2e.sourcelookup.internal.SourceLookupMessages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.StandardClasspathProvider;
import org.eclipse.m2e.jdt.IClasspathManager;
import org.eclipse.m2e.jdt.internal.launch.MavenSourcePathProvider;
import org.eclipse.osgi.util.NLS;

/**
 * Compute and provide source containers from a maven managed java project.
 *
 * TODO(michael) chose a more appropriate class name
 *
 * @author Björn Michael
 * @since 1.0
 */
class MyMvnSourceContainer extends CompositeSourceContainer {

    private final StandardClasspathProvider mavenRuntimeClasspathProvider = new MavenSourcePathProvider();

    private final IJavaProject jp;

    MyMvnSourceContainer(IJavaProject javaProject) {
        jp = javaProject;
    }

    @Override
    public String getName() {
        return NLS.bind(SourceLookupMessages.MyMvnSourceContainer_Name, getProjectName());
    }

    @Override
    public ISourceContainerType getType() {
        return getSourceContainerType(MyMvnSourceContainerTypeDelegate.TYPE_ID);
    }

    @Override
    protected ISourceContainer[] createSourceContainers() throws CoreException {
        return fromMavenSourcePathProvider();
        // return fromJavaRuntimeResolver();
    }

    IJavaProject getJavaProject() {
        return jp;
    }

    String getProjectName() {
        return jp.getElementName();
    }

    private ISourceContainer[] fromJavaRuntimeResolver() throws CoreException {
        for (IClasspathEntry cpe : jp.getRawClasspath()) {
            if (IClasspathEntry.CPE_CONTAINER == cpe.getEntryKind() && //
                    IClasspathManager.CONTAINER_ID.equals(cpe.getPath().toString())) {
                IRuntimeClasspathEntry newRuntimeContainerClasspathEntry = JavaRuntime
                        .newRuntimeContainerClasspathEntry(cpe.getPath(), IRuntimeClasspathEntry.USER_CLASSES, jp);

                IRuntimeClasspathEntry[] resolveRuntimeClasspathEntry = JavaRuntime
                        .resolveRuntimeClasspathEntry(newRuntimeContainerClasspathEntry, jp);

                // there is only one maven2 classpath container in a project return
                return JavaRuntime.getSourceContainers(resolveRuntimeClasspathEntry);
            }
        }

        return new ISourceContainer[] {};
    }

    private ISourceContainer[] fromMavenSourcePathProvider() throws CoreException {

        IRuntimeClasspathEntry mavenEntry = JavaRuntime.newRuntimeContainerClasspathEntry(
                new Path(IClasspathManager.CONTAINER_ID), IRuntimeClasspathEntry.USER_CLASSES);

        ILaunchConfiguration launchConfiguration = getDirector().getLaunchConfiguration();
        // ILaunchConfigurationWorkingCopy wc =
        // launchConfiguration.getWorkingCopy();
        // wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
        // getProjectName());
        // ILaunchConfiguration doSave = wc.doSave();
        ILaunchConfiguration javaProjectLaunchConfiguration = new JavaProjectLaunchConfiguration(launchConfiguration,
                this);

        IRuntimeClasspathEntry[] resolved = mavenRuntimeClasspathProvider
                .resolveClasspath(new IRuntimeClasspathEntry[] { mavenEntry }, javaProjectLaunchConfiguration);

        // IRuntimeClasspathEntry[] entries =
        // JavaRuntime.computeUnresolvedSourceLookupPath(doSave);
        // IRuntimeClasspathEntry[] resolved =
        // JavaRuntime.resolveSourceLookupPath(entries, doSave);

        return JavaRuntime.getSourceContainers(resolved);
    }

}
