import acm.program.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.*;

public class GraphicsGame extends GraphicsProgram {
	 // Here are all of the constants
	public static final int PROGRAM_WIDTH = 500;
	public static final int PROGRAM_HEIGHT = 500;
	public static final String LABEL_FONT = "Arial-Bold-25";
	public static final String EXIT_SIGN = "EXIT";
	public static final String IMG_FILENAME_PATH = "images/";
	public static final String IMG_EXTENSION = ".png";
	public static final String VERTICAL_IMG_FILENAME = "_vert";

	// declare instance variables here
	Level level;
	GLabel numMoves;
	String carName;
	ArrayList<Vehicle> allCars;
	private GObject toDrag;
	double originalX;
	double originalY;
	double lastX;
	double lastY;
	Vehicle clickedVehicle;

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		level = new Level(6,6);
		drawGridLines();
		drawCars();
		drawWinningTile();
		drawLevel();
		gameWon();
		
		addMouseListeners();
	}

	private void drawLevel() {
		// draws the entire level, which should
		// mostly be calls to some of the helper functions.
		numMoves = new GLabel(String.valueOf(level.getNumMoves()),0,spaceHeight());
		numMoves.setFont("Arial-Bold-35");
		add(numMoves);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("Mouse clicked");
		// Space s = convertXYToSpace(e.getX(), e.getY());
		// System.out.println(s);
		clickedVehicle = getVehicleFromXY(e.getX(),e.getY());
		originalX = e.getX();
		originalY = e.getY();

		lastX = e.getX();
		lastY = e.getY();
		toDrag = getElementAt(e.getX(), e.getY());
		if (!(toDrag instanceof GImage))
			return;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (toDrag instanceof GLabel)
			return;
		if (toDrag != null) {
			if(clickedVehicle.isVertical()) {
				toDrag.move(0, e.getY() - lastY);
				lastY = e.getY();
			}
			else {
				toDrag.move(e.getX() - lastX, 0);
				lastX = e.getX();
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (toDrag instanceof GLabel)
			return;
		if (toDrag != null) {
			int spaces = calculateSpacesMoved(originalX,originalY,e.getX(),e.getY(), clickedVehicle);
			if (level.moveNumSpaces(clickedVehicle.getStart(), spaces) == false) {
				toDrag.setLocation(calcStartX(clickedVehicle),calcStartY(clickedVehicle));
			}
			else {
				if (clickedVehicle.isVertical()) {
					toDrag.setLocation(calcStartX(clickedVehicle), calcStartY(clickedVehicle));
				}
				else {
					toDrag.setLocation(calcStartX(clickedVehicle), calcStartY(clickedVehicle));
				}
				numMoves.setLabel(String.valueOf(level.getNumMoves()));
				gameWon();
			}
		}
	}
	
	private void gameWon() {
		if (level.passedLevel()) {
			removeAll();
			GLabel won = new GLabel("Congratulations, you won!", 50,200);
			won.setFont("Arial-Bold-30");
			won.sendToFront();
			add(won);
		}
	}
	
	

	/**
	 * This should draw the label EXIT and add it to the space that represents
	 * the winning tile.
	 */
	private void drawWinningTile() {
		Space exit = level.getWinningSpace();
		GLabel x = new GLabel(EXIT_SIGN, exit.getCol() * spaceWidth(), exit.getRow() * spaceHeight() + (spaceHeight() / 2));
		x.setColor(Color.red);
		x.setFont(LABEL_FONT);
		add(x);
		x.sendToFront();
		
	}

	/**
	 * Should draw the number of grids based on the number of
	 * rows and columns in Level
	 */
	private void drawGridLines() {
		for (int i = 0; i <= level.getColumns(); i++) {
			GLine g = new GLine(i * spaceWidth(), 0, i * spaceWidth(), PROGRAM_HEIGHT);
			add(g);
		}
		for (int i = 0; i <= level.getRows(); i++) {
			GLine g = new GLine(0, i * spaceHeight(), PROGRAM_WIDTH, i * spaceHeight());
			add(g);
		}
	}

	/**
	 * given a list of all the cars, go through them and call
	 * drawCar on each
	 */
	private void drawCars() {
		allCars = level.getVehiclesOnBoard();
		for (int i = 0; i < allCars.size(); i++) {
			drawCar(allCars.get(i));
		}
	}
	
	private void drawCar(Vehicle v) {
		GImage car;
		double size;
		if (v.isVertical()) {
			carName = IMG_FILENAME_PATH + v.getVehicleType().toString() + VERTICAL_IMG_FILENAME + IMG_EXTENSION;
			car = new GImage(carName,calcStartX(v),calcStartY(v));
			if (v.getVehicleType() == VehicleType.TRUCK) {
				car.setSize(spaceWidth(), spaceHeight() * 3);
			}
			else
				car.setSize(spaceWidth(), spaceHeight() * 2);
			
		}
		else {
			carName = IMG_FILENAME_PATH + v.getVehicleType().toString() + IMG_EXTENSION;
			car = new GImage(carName,calcStartX(v),calcStartY(v));
			if (v.getVehicleType() == VehicleType.TRUCK) {
				car.setSize(spaceWidth() * 3, spaceHeight());
			}
			else
				car.setSize(spaceWidth() * 2, spaceHeight());
		}
		
		add(car);
	}

	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those x
	 * and y coordinates, returning null if no Vehicle currently sits at those
	 * coordinates.
	 */
	private Vehicle getVehicleFromXY(double x, double y) {
		Space s = convertXYToSpace(x,y);
		return level.getVehicle(s);
	}

	/**
	 * This is a useful helper function to help you calculate the number of
	 * spaces that a vehicle moved while dragging so that you can then send that
	 * information over as numSpacesMoved to that particular Vehicle object.
	 */
	private int calculateSpacesMoved(double startX, double startY, double endX, double endY, Vehicle v) {
		double xMoves = endX - startX;
		double yMoves = endY -startY;
		
		if (v.isVertical())
			return (int)(yMoves / (spaceHeight() - 5));
		else
			return (int)(xMoves / (spaceWidth() - 5));
	}

	/**
	 * Another helper function/method meant to return the space given an x and y
	 * coordinate system. Use this to help you write getVehicleFromXY
	 */
	private Space convertXYToSpace(double x, double y) {
		Space s = new Space((int)(y / spaceHeight()), (int)(x / spaceWidth()));
		return s;
	}

	 // return the width (in pixels) of a single space in the grid
	private double spaceWidth() {
		// TODO fix this method
		return PROGRAM_WIDTH / level.getColumns();
	}

	 // return the height in pixels of a single space in the grid
	private double spaceHeight() {
		return PROGRAM_HEIGHT / level.getRows();
	}
	
	private double calcStartX(Vehicle v) {
		return v.getStartCol() * spaceWidth();
	}
	
	private double calcStartY(Vehicle v) {
		return v.getStartRow() * spaceHeight();
	}
	
	public static void main(String[] args) {
		new GraphicsGame().start();
	}
}
