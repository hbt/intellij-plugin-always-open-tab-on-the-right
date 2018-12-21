package com.hbt.tabs.placement;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.TabsListener;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class AlwaysTabRightProjectComponent implements ProjectComponent {
    private final static Logger logger = Logger.getInstance(AlwaysTabRightProjectComponent.class);


    public static String LAST_OPENED_FILE_PATH = null;

    private MessageBusConnection connection;


    private final Project project;
    @NotNull
    private final AlwaysTabRightApplicationComponent applicationComponent;

    /**
     * @param project The current project, i.e. the project which was just opened.
     */
    public AlwaysTabRightProjectComponent(Project project, @NotNull AlwaysTabRightApplicationComponent applicationComponent) {
        this.project = project;
        this.applicationComponent = applicationComponent;
    }

    public void initComponent() {
//        logger.setLevel(Level.ALL);

        logger.debug("init");
        MessageBus bus = ApplicationManager.getApplication().getMessageBus();
        connection = bus.connect();


        connection.subscribe(FileEditorManagerListener.Before.FILE_EDITOR_MANAGER, new FileEditorManagerListener.Before() {
                    @Override
                    public void beforeFileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

                        logger.debug("File Opened" + file.getCanonicalPath());
                        LAST_OPENED_FILE_PATH = file.getCanonicalPath();


                        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(source.getProject());

                        for (EditorWindow editorWindow : manager.getWindows()) {
                            JBTabs tabs = manager.getCurrentWindow().getTabbedPane().getTabs();

                            editorWindow.getTabbedPane().getTabs().addListener(new TabsListener() {
                                @Override
                                public void selectionChanged(TabInfo oldSelection, TabInfo newSelection) {
                                    if (LAST_OPENED_FILE_PATH != null) {
                                        LAST_OPENED_FILE_PATH = null;

                                        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(source.getProject());

                                        logger.debug("TAB CHANGE: " + oldSelection.getText() + "----" + newSelection.getText());
                                        logger.debug("TABS COUNT: " + tabs.getTabCount());

                                        tabs.removeTab(newSelection);

                                        int oldIndex = tabs.getIndexOf(oldSelection);
                                        int newIndex = tabs.getIndexOf(newSelection);

                                        for (int i = 0; i < tabs.getTabCount(); i++) {

                                            TabInfo tt = tabs.getTabAt(i);
                                            logger.debug("Tab Title: " + i + "----" + tt.getText());
                                            logger.debug("Tab Index: " + i + "----" + tabs.getIndexOf(tt));
                                        }

                                        logger.debug("Tab Old index:" + oldIndex);
                                        logger.debug("Tab New Index:" + newIndex);

                                        tabs.addTab(newSelection, oldIndex + 1);
                                        tabs.select(newSelection, true);

                                    }
                                }


                            });

                        }


                    }
                }

        );


    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }

    public void disposeComponent() {
        //called after projectClosed()
    }

    @NotNull
    public String getComponentName() {
        return "AlwaysTabRightProjectComponent";
    }
}
