package m2e.sourcelookup;

import m2e.sourcelookup.internal.SourceLookupMessages;

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

  private final StandardClasspathProvider mavenRuntimeClasspathProvider = new MavenSourcePathProvider() {
    @Override
    protected int getArtifactScope(final ILaunchConfiguration configuration) {
      return IClasspathManager.CLASSPATH_RUNTIME;
    }
  };

  private final IJavaProject jp;

  MyMvnSourceContainer(final IJavaProject javaProject) {
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
    for (final IClasspathEntry cpe : jp.getRawClasspath()) {
      if (IClasspathEntry.CPE_CONTAINER == cpe.getEntryKind() && //
          IClasspathManager.CONTAINER_ID.equals(cpe.getPath().toString())) {
        final IRuntimeClasspathEntry newRuntimeContainerClasspathEntry = JavaRuntime.newRuntimeContainerClasspathEntry(cpe.getPath(),
            IRuntimeClasspathEntry.USER_CLASSES, jp);

        final IRuntimeClasspathEntry[] resolveRuntimeClasspathEntry = JavaRuntime.resolveRuntimeClasspathEntry(
            newRuntimeContainerClasspathEntry, jp);

        // there is only one maven2 classpath container in a project return
        return JavaRuntime.getSourceContainers(resolveRuntimeClasspathEntry);
      }
    }

    return new ISourceContainer[] {};
  }

  private ISourceContainer[] fromMavenSourcePathProvider() throws CoreException {

    final IRuntimeClasspathEntry mavenEntry = JavaRuntime.newRuntimeContainerClasspathEntry(new Path(IClasspathManager.CONTAINER_ID),
        IRuntimeClasspathEntry.USER_CLASSES);

    final ILaunchConfiguration launchConfiguration = getDirector().getLaunchConfiguration();
    // final ILaunchConfigurationWorkingCopy wc = launchConfiguration.getWorkingCopy();
    // wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, getProjectName());
    // final ILaunchConfiguration doSave = wc.doSave();
    final ILaunchConfiguration javaProjectLaunchConfiguration = new JavaProjectLaunchConfiguration(launchConfiguration, this);

    final IRuntimeClasspathEntry[] resolved = mavenRuntimeClasspathProvider.resolveClasspath(new IRuntimeClasspathEntry[] {
      mavenEntry
    }, javaProjectLaunchConfiguration);

    // final IRuntimeClasspathEntry[] entries = JavaRuntime.computeUnresolvedSourceLookupPath(doSave);
    // final IRuntimeClasspathEntry[] resolved = JavaRuntime.resolveSourceLookupPath(entries, doSave);

    return JavaRuntime.getSourceContainers(resolved);
  }

}
