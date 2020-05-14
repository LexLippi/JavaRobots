package gui;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class LogWindow extends JInternalFrame implements LogChangeListener, Serializable, Reopenable, Multilangual
{
    private LogWindow window = this;
    private transient LogWindowSource m_logSource;
    private TextArea m_logContent;
    private ClosingHandler closingHandler = new ClosingHandler();
    private ResourceBundle m_bundle;

    public LogWindow(LogWindowSource logSource, ResourceBundle bundle)
    {
        super(bundle.getString("logTitleKey"), true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        m_bundle = bundle;
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.LOG);
            }
        });
    }

    public void setMetadata(ResourceBundle bundle) {
        m_logSource = Logger.getDefaultLogSource();
        m_logSource.registerListener(this);
        createNewClosingHandler(bundle);
    }

    public void changeLanguage(ResourceBundle nextBundle){
        m_bundle = nextBundle;
        this.setTitle(nextBundle.getString("logTitleKey"));
        updateLogContent();
    }

    private void createNewClosingHandler(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, ClosingHandler.ClosingType.LOG);
            }
        });
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(m_bundle.getString(entry.getMessage())).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        m_logSource.unregisterListener(this);
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
