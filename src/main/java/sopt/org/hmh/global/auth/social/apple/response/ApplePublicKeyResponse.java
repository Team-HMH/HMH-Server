package sopt.org.hmh.global.auth.social.apple.response;

public record ApplePublicKeyResponse(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e) {
}