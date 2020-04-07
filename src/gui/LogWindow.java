package gui;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class LogWindow extends JInternalFrame implements LogChangeListener, Serializable
{
    private LogWindow window = this;
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private ClosingHandler closingHandler = new ClosingHandler();

    public LogWindow(LogWindowSource logSource, ResourceBundle bundle)
    {
        super(bundle.getString("logTitleKey"), true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
                closingHandler.handleClosing(window, e, bundle, 2);
            }
        });
    }

    public void setMetadata(LogWindowSource logSource, ResourceBundle bundle) {
        createNewLogSourceWithOldMessages(logSource);
        createNewClosingHandler(bundle);
    }

    private void createNewLogSourceWithOldMessages(LogWindowSource logSource) {
        var entries = m_logSource.all();
        m_logSource = logSource;
        m_logSource.registerListener(this);
        for (var entry: entries) {
            m_logSource.append(entry.getLevel(), entry.getMessage());
        }
    }

    private void createNewClosingHandler(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                closingHandler.handleClosing(window, e, bundle, 2);
            }
        });
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
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
        System.out.println("here Alex");
        EventQueue.invokeLater(this::updateLogContent);
    }
}
