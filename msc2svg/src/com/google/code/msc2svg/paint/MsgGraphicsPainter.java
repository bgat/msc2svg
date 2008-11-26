/**
 * Author: Aziz Hammadi
 *
 * This file is part of msc2svg.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package com.google.code.msc2svg.paint;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.*;

import com.google.code.msc2svg.Context;
import com.google.code.msc2svg.model.Message;

public class MsgGraphicsPainter {
    Message msg;
	Color color = Color.ORANGE;

	Point startPoint;

	Context context;

	public static enum MODE {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT, LEFT_AND_RIGHT
	};

	public MsgGraphicsPainter() {

	}

	public void paint(MsgGraphicsPainter.MODE mode, int msgMaxLength) {

		if (this.msg.getMsg().trim().length() == 0)
			return;

		Graphics2D g2d = this.context.getSvgGenerator();
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D msgTextRec = fm.getStringBounds(this.msg.getMsg(), g2d);
		
		Color oldColor =g2d.getColor(); 
		g2d.setColor(this.msg.getColor());

		
		if (mode == MODE.LEFT_AND_RIGHT) {
			int msgFrameRecWidth = msgMaxLength;
			int msgFrameRecHeight = (int) msgTextRec.getHeight()
					+ this.context.getPadding();

			Point pointA = new Point();
			pointA.x = this.startPoint.x - this.context.getPadding();
			pointA.y = this.startPoint.y;

			Point pointM = new Point();
			pointM.x = pointA.x + this.context.getPadding();
			pointM.y = pointA.y - msgFrameRecHeight / 2;

			Point pointP = new Point();
			pointP.x = pointM.x;
			pointP.y = pointA.y + msgFrameRecHeight / 2;

			Point pointK = new Point();
			pointK.x = pointM.x + msgFrameRecWidth;
			pointK.y = pointM.y;

			Point pointL = new Point();
			pointL.x = pointP.x + msgFrameRecWidth;
			pointL.y = pointP.y;

			Point pointD = new Point();
			pointD.x = pointA.x + msgFrameRecWidth + this.context.getPadding()
					* 2;
			pointD.y = pointA.y;

			Polygon polygon = new Polygon();
			polygon.addPoint(pointA.x, pointA.y);
			polygon.addPoint(pointM.x, pointM.y);
			polygon.addPoint(pointK.x, pointK.y);
			polygon.addPoint(pointD.x, pointD.y);
			polygon.addPoint(pointL.x, pointL.y);
			polygon.addPoint(pointP.x, pointP.y);
			g2d.draw(polygon);
			
			
			
			// {{ Point E
			Point pointE = new Point();
			pointE.x = pointM.x + (msgFrameRecWidth - (int)msgTextRec.getWidth())/2;
			pointE.y = pointM.y + (msgFrameRecHeight - fm.getHeight()) / 2
					+ fm.getAscent();
			// }}
				
			g2d.drawString(this.msg.getMsg(), (int) pointE.x, (int) pointE.y);

			
			
		} else {

			int msgFrameRecWidth = (int) msgTextRec.getWidth()
					+ this.context.getPadding();
			int msgFrameRecHeight = (int) msgTextRec.getHeight()
					+ this.context.getPadding();

			Rectangle msgFrameRec = new Rectangle();
			msgFrameRec.setBounds(0, 0, msgFrameRecWidth, msgFrameRecHeight);
			// msgFrameRec.x and msgFrameRec.y will be set later to pointM.x and
			// pointM.y

			// {{ Point A
			Point pointA = new Point();
			pointA.setLocation(this.startPoint);
			// }}

			// {{ Point B
			Point pointB = new Point();
			int legnthAB = (msgMaxLength - msgFrameRecWidth) / 2;
			pointB.x = (int) pointA.getX() + legnthAB;
			pointB.y = (int) pointA.y;
			// }}

			// {{ Point M
			Point pointM = new Point();
			pointM.x = (int) pointB.getX();
			pointM.y = (int) pointB.y - msgFrameRecHeight / 2;

			// set pointM as the start point of msgFrameRec
			msgFrameRec.x = (int) pointM.x;
			msgFrameRec.y = (int) pointM.y;
			// }}

			// {{ Point C
			Point pointC = new Point();
			pointC.x = (int) pointB.getX() + msgFrameRecWidth;
			pointC.y = (int) pointA.y;
			// }}

			// {{ Point D
			Point pointD = new Point();
			int lengthCD = legnthAB;
			pointD.x = (int) pointC.getX() + lengthCD;
			pointD.y = (int) pointC.y;
			// }}

			// {{ Point E
			Point pointE = new Point();
			pointE.x = pointM.x + this.context.getPadding() / 2;
			pointE.y = pointM.y + (msgFrameRecHeight - fm.getHeight()) / 2
					+ fm.getAscent();
			// }}

			// do the drawing ..

			int arrowSize = (int) this.context.getMsgGraphicDimension().height / 2;

			Polygon arrow = new Polygon();

			Point pointP = new Point();
			Point pointQ = new Point();

			switch (mode) {
			case LEFT_TO_RIGHT: {
				pointP.x = (int) pointD.x - arrowSize;
				pointP.y = (int) pointD.y + arrowSize;

				pointQ = new Point();
				pointQ.x = (int) pointD.x - arrowSize;
				pointQ.y = (int) pointD.y - arrowSize;

				arrow.addPoint((int) pointP.x, (int) pointP.y);
				arrow.addPoint((int) pointD.x, (int) pointD.y);
				arrow.addPoint((int) pointQ.x, (int) pointQ.y);

				pointD.x = pointQ.x;
				break;

			}
			case RIGHT_TO_LEFT: {
				pointP.x = (int) pointA.x + arrowSize;
				pointP.y = (int) pointA.y + arrowSize;

				pointQ = new Point();
				pointQ.x = (int) pointA.x + arrowSize;
				pointQ.y = (int) pointA.y - arrowSize;

				arrow.addPoint((int) pointP.x, (int) pointP.y);
				arrow.addPoint((int) pointA.x, (int) pointA.y);
				arrow.addPoint((int) pointQ.x, (int) pointQ.y);

				pointA.x = pointQ.x;
				break;

			}
			default: {

			}
			}

			if (this.context.isArrawFill()) {
				g2d.fill(arrow);
			} else {
				g2d.draw(arrow);
			}
			
			g2d.drawLine((int) pointA.getX(), (int) pointA.y, (int) pointB
					.getX(), (int) pointB.y);
			g2d.draw(msgFrameRec);
			g2d.drawString(this.msg.getMsg(), (int) pointE.x, (int) pointE.y);
			g2d.drawLine((int) pointC.x, (int) pointC.y, (int) pointD.x,
					(int) pointD.y);
			
			g2d.setColor(oldColor);

		}
	}

	public void paintProcessRect(String text, Point lineStartPoint) {

		Graphics2D g2d = this.context.getSvgGenerator();
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D msgTextRec = fm.getStringBounds(text, g2d);

		int msgFrameRecWidth = (int) msgTextRec.getWidth()
				+ this.context.getPadding();
		int msgFrameRecHeight = (int) msgTextRec.getHeight()
				+ this.context.getPadding();

		// {{ Point M
		Point pointM = new Point();
		pointM.x = lineStartPoint.x - msgFrameRecWidth / 2;
		pointM.y = lineStartPoint.y - msgFrameRecHeight;

		// set pointM as the start point of msgFrameRec

		Rectangle msgFrameRec = new Rectangle();
		msgFrameRec.setBounds((int) pointM.x, (int) pointM.y, msgFrameRecWidth,
				msgFrameRecHeight);
		// }}

		// {{ Point E
		Point pointE = new Point();
		pointE.x = pointM.x + (msgFrameRecWidth - (int) msgTextRec.getWidth())
				/ 2;
		pointE.y = pointM.y + (msgFrameRecHeight - fm.getHeight()) / 2
				+ fm.getAscent();
		// }}

		g2d.draw(msgFrameRec);
		g2d.drawString(text, (int) pointE.x, (int) pointE.y);

	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
