package bjmi.m2e.sourcelookup;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * TODO (michael)
 * 
 * @author Björn Michael
 * @since 1.1
 */
public class JavaProjectLaunchConfiguration implements ILaunchConfiguration {

  private final ILaunchConfiguration delegate;

  private final MyMvnSourceContainer project;

  JavaProjectLaunchConfiguration(final ILaunchConfiguration origin, final MyMvnSourceContainer sourceContainer) {
    delegate = origin;
    project = sourceContainer;
  }

  @Override
  public boolean contentsEqual(final ILaunchConfiguration configuration) {
    return delegate.contentsEqual(configuration);
  }

  @Override
  public ILaunchConfigurationWorkingCopy copy(final String name) throws CoreException {
    return delegate.copy(name);
  }

  @Override
  public void delete() throws CoreException {
    delegate.delete();
  }

  @Override
  public boolean exists() {
    return delegate.exists();
  }

  @Override
  public Object getAdapter(final Class adapter) {
    return delegate.getAdapter(adapter);
  }

  @Override
  public boolean getAttribute(final String attributeName, final boolean defaultValue) throws CoreException {
    return delegate.getAttribute(attributeName, defaultValue);
  }

  @Override
  public int getAttribute(final String attributeName, final int defaultValue) throws CoreException {
    return delegate.getAttribute(attributeName, defaultValue);
  }

  @Override
  public List getAttribute(final String attributeName, final List defaultValue) throws CoreException {
    return delegate.getAttribute(attributeName, defaultValue);
  }

  @Override
  public Map getAttribute(final String attributeName, final Map defaultValue) throws CoreException {
    return delegate.getAttribute(attributeName, defaultValue);
  }

  @Override
  public Set getAttribute(final String attributeName, final Set defaultValue) throws CoreException {
    return delegate.getAttribute(attributeName, defaultValue);
  }

  @Override
  public String getAttribute(final String attributeName, final String defaultValue) throws CoreException {
    final String attribute = delegate.getAttribute(attributeName, defaultValue);

    if (IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME.equals(attributeName)) {
      if (attribute == null || attribute.isEmpty()) {
        return project.getProjectName();
      }
    }

    return attribute;
  }

  @Override
  public Map getAttributes() throws CoreException {
    return delegate.getAttributes();
  }

  @Override
  public String getCategory() throws CoreException {
    return delegate.getCategory();
  }

  @Override
  public IFile getFile() {
    return delegate.getFile();
  }

  @Override
  public IPath getLocation() {
    return delegate.getLocation();
  }

  @Override
  public IResource[] getMappedResources() throws CoreException {
    return delegate.getMappedResources();
  }

  @Override
  public String getMemento() throws CoreException {
    return delegate.getMemento();
  }

  @Override
  public Set getModes() throws CoreException {
    return delegate.getModes();
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public ILaunchDelegate getPreferredDelegate(final Set modes) throws CoreException {
    return delegate.getPreferredDelegate(modes);
  }

  @Override
  public ILaunchConfigurationType getType() throws CoreException {
    return delegate.getType();
  }

  @Override
  public ILaunchConfigurationWorkingCopy getWorkingCopy() throws CoreException {
    return delegate.getWorkingCopy();
  }

  @Override
  public boolean hasAttribute(final String attributeName) throws CoreException {
    return delegate.hasAttribute(attributeName);
  }

  @Override
  public boolean isLocal() {
    return delegate.isLocal();
  }

  @Override
  public boolean isMigrationCandidate() throws CoreException {
    return delegate.isMigrationCandidate();
  }

  @Override
  public boolean isReadOnly() {
    return delegate.isReadOnly();
  }

  @Override
  public boolean isWorkingCopy() {
    return delegate.isWorkingCopy();
  }

  @Override
  public ILaunch launch(final String mode, final IProgressMonitor monitor) throws CoreException {
    return delegate.launch(mode, monitor);
  }

  @Override
  public ILaunch launch(final String mode, final IProgressMonitor monitor, final boolean build) throws CoreException {
    return delegate.launch(mode, monitor, build);
  }

  @Override
  public ILaunch launch(final String mode, final IProgressMonitor monitor, final boolean build, final boolean register)
      throws CoreException {
    return delegate.launch(mode, monitor, build, register);
  }

  @Override
  public void migrate() throws CoreException {
    delegate.migrate();
  }

  @Override
  public boolean supportsMode(final String mode) throws CoreException {
    return delegate.supportsMode(mode);
  }

}
