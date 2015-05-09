package bjmi.m2e.sourcelookup.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author Björn Michael
 * @since 1.0
 */
public class SourceLookupPlugin extends Plugin {

  // The shared instance
  private static SourceLookupPlugin plugin;

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static SourceLookupPlugin getInstance() {
    return plugin;
  }

  @Override
  public void start(final BundleContext bundleContext) throws Exception {
    super.start(bundleContext);
    plugin = this;
  }

  @Override
  public void stop(final BundleContext bundleContext) throws Exception {
    plugin = null;
    super.stop(bundleContext);
  }

}
