package bjmi.m2e.sourcelookup.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Eclipse messages for l10n.
 *
 * <strong>Messages key declaration must NOT contain final.</strong>
 *
 * @author Björn Michael
 * @since 1.1
 */
@SuppressWarnings("javadoc")
public final class SourceLookupMessages {

  public static String MyMvnSourceContainer_Name;

  public static String MyMvnSourceContainerBrowser_Dialog_Title;

  public static String myMvnSourceContainerTypeDelegate_ContainerIsMissing;

  public static String MyMvnSourceContainerTypeDelegate_InvalidFormat;

  public static String MyMvnSourceContainerTypeDelegate_ProjectNameIsMissing;

  private static final String BUNDLE_NAME = "bjmi.m2e.sourcelookup.internal.SourceLookupMessages";

  static {
    NLS.initializeMessages(BUNDLE_NAME, SourceLookupMessages.class);
  }

  private SourceLookupMessages() {
    // prevent instantiation
    throw new UnsupportedOperationException("invocation of sealed constructor");
  }

}
