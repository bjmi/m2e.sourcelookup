package m2e.sourcelookup;

import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaSourceLookupParticipant;
import org.eclipse.m2e.internal.launch.MavenSourceLocator;

/**
 * Adds {@link JavaSourceLookupParticipant} to this {@link ISourceLookupDirector source lookup director} if not present and overrides
 * extension {@code org.eclipse.m2e.launching.MavenSourceLocator} at extension point {@code org.eclipse.debug.core.sourceLocators} in
 * {@code plugin.xml}.
 * <p>
 * MavenLaunchExtensionsSupport.configureSourceLookup(ILaunchConfiguration, ILaunch, IProgressMonitor) configures
 * {@link ISourceLookupParticipant}s at launch time only once. org.eclipse.m2e.internal.launch.MavenSourceLocator
 *
 * @author Björn Michael
 * @since 1.1.3
 */
public class MyMvnSourceLocator extends MavenSourceLocator {

  @Override
  public void initializeParticipants() {
    super.initializeParticipants();

    // search for existing JavaSourceLookupParticipant
    // this is added only once after calling
    // org.eclipse.m2e.internal.launch.MavenLaunchDelegate.launch(ILaunchConfiguration, String, ILaunch, IProgressMonitor)
    // -->
    // org.eclipse.m2e.internal.launch.MavenLaunchExtensionsSupport.configureSourceLookup(ILaunchConfiguration, ILaunch, IProgressMonitor)
    for (final ISourceLookupParticipant participant : getParticipants()) {
      if (participant instanceof JavaSourceLookupParticipant) {
        return;
      }
    }
    addParticipants(new ISourceLookupParticipant[] {
      new JavaSourceLookupParticipant()
    });
  }

}
