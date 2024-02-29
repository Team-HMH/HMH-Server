package sopt.org.hmh.global.auth.social.apple.request;

public record ApplePublicKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e) {
}