package br.com.matteusmoreno.uol_backend_challenge.exception;

public class GroupMaxPlayersReachedException extends RuntimeException {
    public GroupMaxPlayersReachedException() {
        super("The maximum number of players has been reached.");
    }
}
