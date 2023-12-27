package Laby;

public enum Direction {
	  UP(new Vector2D(0, 1)),
	  DOWN(new Vector2D(0, -1)),
	  LEFT(new Vector2D(-1, 0)),
	  RIGHT(new Vector2D(1, 0));

	  public final Vector2D vector;

	  Direction(Vector2D vector) {
	      this.vector = vector;
	  }
	}
