package homework1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);

		this.parent = pnlParent;
        JButton button = new JButton("Add");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        DefaultListModel listModel = new DefaultListModel();
		GeoSegment[] segments = ExampleGeoSegments.segments;
		for(int i = 0; i < segments.length ; i++) {
			listModel.addElement(segments[i]);
		}
		lstSegments = new JList<>(listModel);
        JScrollPane segmentListPane = new JScrollPane(lstSegments);
        segmentListPane.setPreferredSize(new Dimension(440, 400));
        segmentListPane.setAlignmentX(LEFT_ALIGNMENT);

        JPanel segmentListPanel = new JPanel();
        segmentListPanel.setLayout(new BoxLayout(segmentListPanel, BoxLayout.Y_AXIS));
        JLabel geoSegmentLabel = new JLabel("GeoSegments");
        geoSegmentLabel.setLabelFor(segmentListPanel);
        segmentListPanel.add(geoSegmentLabel);
        segmentListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        segmentListPanel.add(segmentListPane);
        segmentListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlParent.addSegment((GeoSegment) lstSegments.getSelectedValue());
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(cancelButton);



        getContentPane().add(segmentListPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        pack();
	}
}
