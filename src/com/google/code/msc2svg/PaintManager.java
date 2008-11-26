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

package com.google.code.msc2svg;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.csvreader.CsvReader;
import com.google.code.msc2svg.model.Message;

public class PaintManager {

	CsvReader csvReader = null;

	Context context;

	int columnMaxLength[];

	public PaintManager(Context context) {
		this.context = context;
	}

	public void parseDescriptionFile() throws IOException {
		this.loadFile();
		this.columnMaxLength = new int[this.csvReader.getHeaderCount()];

		while (csvReader.readRecord()) {
			/* record = HCI_OPEN> ; L2CAP_OPEN> ; MSG_OPEN */
			for (int i = 0; i < csvReader.getHeaderCount(); i++) {
				/* compute the width of the message name once it is represented as a graphic */
				double msgWidth = this.context.getSvgGenerator().getFont()
						.createGlyphVector(
								this.context.getSvgGenerator()
										.getFontRenderContext(),
								csvReader.get(i)).getPixelBounds(
								this.context.getSvgGenerator()
										.getFontRenderContext(), 0, 0)
						.getWidth();

				/* compute the height of the message name once it is represented as a graphic */
				double msgHeight = this.context.getSvgGenerator().getFont()
						.createGlyphVector(
								this.context.getSvgGenerator()
										.getFontRenderContext(),
								csvReader.get(i)).getPixelBounds(
								this.context.getSvgGenerator()
										.getFontRenderContext(), 0, 0)
						.getHeight();

				/* set the columnMaxLength */
				if (columnMaxLength[i] < msgWidth) {
					columnMaxLength[i] = (int) msgWidth;
				}

				if ((int) msgWidth > this.context.getMsgGraphicDimension().width) {
					this.context.getMsgGraphicDimension().width = (int) msgWidth;
				}
				if ((int) msgHeight > this.context.getMsgGraphicDimension().height) {
					this.context.getMsgGraphicDimension().height = (int) msgHeight;
				}
			}

			this.context
					.setPadding(context.getMsgGraphicDimension().height / 2);
		}

		/*
		 * extend the columnMaxLength
		 * 
		 */
		for (int j = 0; j < columnMaxLength.length; j++) {
			columnMaxLength[j] += 4 * this.context.getMsgGraphicDimension().height;

		}

		this.context.getMsgGraphicDimension().width += 4 * this.context
				.getMsgGraphicDimension().height;
	}

	private void loadFile() {
		try {
			this.csvReader = new CsvReader(this.context
					.getDescriptionFileName(), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			this.csvReader.readHeaders();
			if (this.context.getProcessesArray().length != this.csvReader
					.getHeaderCount() + 1) {
				try {
					throw new Exception(
							"the application.properties file contains "
									+ this.context.getProcessesArray().length
									+ " processes.\n"
									+ "Since the input file contains "
									+ this.csvReader.getHeaderCount()
									+ " entries,"
									+ (this.csvReader.getHeaderCount() + 1)
									+ " processes are expected");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		this.loadFile();
		MsgGraphicsPainter painter = new MsgGraphicsPainter();
		painter.setContext(this.context);
		int recordCount = 0;
		int startX = this.context.getAbsolutStartPoint().x;
		int unit = this.context.getMsgGraphicDimension().height / 2;
		int startY = this.context.getAbsolutStartPoint().y + unit * 2;

		try {
			while (this.csvReader.readRecord()) {
				recordCount++;
				startX = this.context.getAbsolutStartPoint().x;
				startY += 4 * unit;
				for (int i = 0; i < this.csvReader.getHeaderCount(); i++) {

                    String rawMsg = csvReader.get(i);
                    Message msg = new Message();
                    msg.setMsg(rawMsg);

					MsgGraphicsPainter.MODE modeToUse = MsgGraphicsPainter.MODE.LEFT_TO_RIGHT;
					if (rawMsg.endsWith(">")
							&& rawMsg.startsWith("<")) {
						modeToUse = MsgGraphicsPainter.MODE.LEFT_AND_RIGHT;
						
						if (rawMsg.trim().length() != 0) {
							// remove > and < from the end and beginning

							rawMsg = rawMsg.substring(0, rawMsg.length() -1 );// cut '>' from the end
							rawMsg = rawMsg.substring(1, rawMsg.length());    // cut '<' from the start
                            char colorId = rawMsg.charAt(0);
                            msg.setColor((Color)context.getColors().get((new StringBuilder("colors.")).append(colorId).toString()));
                            rawMsg = rawMsg.substring(2, rawMsg.length());
                            msg.setMsg(rawMsg);
						}

						
					}
					else if (rawMsg.endsWith(">")) {
						modeToUse = MsgGraphicsPainter.MODE.LEFT_TO_RIGHT;
						if (rawMsg.trim().length() != 0) {
							// remove > from the end
                            rawMsg = rawMsg.substring(0, rawMsg.length() - 1);
                            char colorId = rawMsg.charAt(0);
                            msg.setColor((Color)context.getColors().get((new StringBuilder("colors.")).append(colorId).toString()));
                            rawMsg = rawMsg.substring(2, rawMsg.length());
                            msg.setMsg(rawMsg);
							
						}
						
					} else if (rawMsg.endsWith("<")) {
						modeToUse = MsgGraphicsPainter.MODE.RIGHT_TO_LEFT;
						if (rawMsg.trim().length() != 0) {
							// remove < from the end
                            rawMsg = rawMsg.substring(0, rawMsg.length() - 1);
                            char colorId = rawMsg.charAt(0);
                            msg.setColor((Color)context.getColors().get((new StringBuilder("colors.")).append(colorId).toString()));
                            rawMsg = rawMsg.substring(2, rawMsg.length());
                            msg.setMsg(rawMsg);
						}
						
					} else if (rawMsg.trim().length() != 0) {
						throw new Exception("not supported mode!");
					}

					startX = this.context.getAbsolutStartPoint().x;
					for (int j = 0; j < i; j++) {
                        startX += columnMaxLength[j];
					}
                    painter.setStartPoint(new Point(startX, startY));
                    painter.setMsg(msg);
					painter.paint(modeToUse, columnMaxLength[i]);
					


				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// draw the process lines
		for (int i = 0; i <= this.csvReader.getHeaderCount(); i++) {
			Point lineStart = new Point();
			Point lineEnd = new Point();

			lineStart.x = context.getAbsolutStartPoint().x;
			lineStart.y = context.getAbsolutStartPoint().y;

			for (int t = 0; t < i; t++) {
				lineStart.x += columnMaxLength[t];
			}

			lineEnd.x = lineStart.x;
			lineEnd.y = startY + 2 * unit;

			Color oldColor = this.context.getSvgGenerator().getColor();
			this.context.getSvgGenerator().setColor(
					this.context.getProcessColor());

			this.context.getSvgGenerator().drawLine(lineStart.x, lineStart.y,
					lineEnd.x, lineEnd.y);
			;

			painter.paintProcessRect(this.context.getProcessesArray()[i],
					lineStart);

			this.context.getSvgGenerator().setColor(oldColor);
		}

	}
}
