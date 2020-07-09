package bjmi.m2e.sourcelookup;

import java.util.Collection;
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

    JavaProjectLaunchConfiguration(ILaunchConfiguration origin, MyMvnSourceContainer sourceContainer) {
        delegate = origin;
        project = sourceContainer;
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return delegate.getAdapter(adapter);
    }

    @Override
    public boolean contentsEqual(ILaunchConfiguration configuration) {
        return delegate.contentsEqual(configuration);
    }

    @Override
    public ILaunchConfigurationWorkingCopy copy(String name) throws CoreException {
        return delegate.copy(name);
    }

    @Override
    public void delete() throws CoreException {
        delegate.delete();
    }

    @Override
    public void delete(int flag) throws CoreException {
        delegate.delete(flag);
    }

    @Override
    public boolean exists() {
        return delegate.exists();
    }

    @Override
    public boolean getAttribute(String attributeName, boolean defaultValue) throws CoreException {
        return delegate.getAttribute(attributeName, defaultValue);
    }

    @Override
    public int getAttribute(String attributeName, int defaultValue) throws CoreException {
        return delegate.getAttribute(attributeName, defaultValue);
    }

    @Override
    public List<String> getAttribute(String attributeName, List<String> defaultValue) throws CoreException {
        return delegate.getAttribute(attributeName, defaultValue);
    }

    @Override
    public Set<String> getAttribute(String attributeName, Set<String> defaultValue) throws CoreException {
        return delegate.getAttribute(attributeName, defaultValue);
    }

    @Override
    public Map<String, String> getAttribute(String attributeName, Map<String, String> defaultValue)
            throws CoreException {
        return delegate.getAttribute(attributeName, defaultValue);
    }

    @Override
    public String getAttribute(String attributeName, String defaultValue) throws CoreException {
        String attribute = delegate.getAttribute(attributeName, defaultValue);

        if (IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME.equals(attributeName)) {
            if (attribute == null || attribute.isEmpty()) {
                return project.getProjectName();
            }
        }

        return attribute;
    }

    @Override
    public Map<String, Object> getAttributes() throws CoreException {
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
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Set<String> getModes() throws CoreException {
        return delegate.getModes();
    }

    @Override
    public ILaunchDelegate getPreferredDelegate(Set<String> modes) throws CoreException {
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
    public boolean hasAttribute(String attributeName) throws CoreException {
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
    public boolean isWorkingCopy() {
        return delegate.isWorkingCopy();
    }

    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor) throws CoreException {
        return delegate.launch(mode, monitor);
    }

    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor, boolean build) throws CoreException {
        return delegate.launch(mode, monitor, build);
    }

    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor, boolean build, boolean register) throws CoreException {
        return delegate.launch(mode, monitor, build, register);
    }

    @Override
    public void migrate() throws CoreException {
        delegate.migrate();
    }

    @Override
    public boolean supportsMode(String mode) throws CoreException {
        return delegate.supportsMode(mode);
    }

    @Override
    public boolean isReadOnly() {
        return delegate.isReadOnly();
    }

    @Override
    public ILaunchConfiguration getPrototype() throws CoreException {
        return delegate.getPrototype();
    }

    @Override
    public boolean isAttributeModified(String attribute) throws CoreException {
        return delegate.isAttributeModified(attribute);
    }

    @Override
    public boolean isPrototype() {
        return delegate.isPrototype();
    }

    @Override
    public Collection<ILaunchConfiguration> getPrototypeChildren() throws CoreException {
        return delegate.getPrototypeChildren();
    }

    @Override
    public int getKind() throws CoreException {
        return delegate.getKind();
    }

    @Override
    public Set<String> getPrototypeVisibleAttributes() throws CoreException {
        return delegate.getPrototypeVisibleAttributes();
    }

    @Override
    public void setPrototypeAttributeVisibility(String attribute, boolean visible) throws CoreException {
        delegate.setPrototypeAttributeVisibility(attribute, visible);
    }

}
