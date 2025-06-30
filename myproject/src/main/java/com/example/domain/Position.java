package com.example.domain;

public final class Position {
    public final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position left() { return move(-1, 0); }
    public Position right() { return move(1, 0); }
    public Position down() { return move(0, 1); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
} 