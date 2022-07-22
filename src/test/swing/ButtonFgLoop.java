/*
 * Copyright (c) 2005-2018 Trident Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Trident Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package test.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.RepeatBehavior;

public class ButtonFgLoop extends JFrame {
    public ButtonFgLoop() {
        this.setLayout(new FlowLayout());

        JButton buttonWithCancel = createButton("button w/cancel");
        final Timeline timelineWithCancel = createTimeline(buttonWithCancel);
        buttonWithCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                timelineWithCancel.playLoop(RepeatBehavior.REVERSE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                timelineWithCancel.cancelAtCycleBreak();
            }
        });
        this.add(buttonWithCancel);

        JButton buttonWithRevert = createButton("button w/revert");
        final Timeline timelineWithRevert = createTimeline(buttonWithRevert);
        buttonWithRevert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                timelineWithRevert.playLoop(RepeatBehavior.REVERSE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                timelineWithRevert.playReverse();
            }
        });
        this.add(buttonWithRevert);

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setForeground(Color.blue);
        return button;
    }

    private Timeline createTimeline(JButton button) {
        Timeline timeline = new Timeline(button);
        timeline.setDuration(1500);
        timeline.addPropertyToInterpolate("foreground", button.getForeground(), Color.red);
        return timeline;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ButtonFgLoop().setVisible(true));
    }
}
