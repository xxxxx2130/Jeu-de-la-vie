package gameoflife;


public class Cellule {
    private boolean alive;
    private Position position;

    /**
     * @param position
     * @param life
     */
    public Cellule(Position position, boolean life) {
        this.position = position;
        this.alive = life;
    }


    /**
     * @return vrai ou faux
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * @return la position
     */
    public Position getPosition() {
		return this.position;
	}

	/**
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (alive ? 1231 : 1237);
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cellule other = (Cellule) obj;
        if (alive != other.alive)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }
    
}
