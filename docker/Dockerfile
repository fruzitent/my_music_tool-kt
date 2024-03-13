# syntax=docker.io/docker/dockerfile:1.6-labs
# @help: https://docs.docker.com/reference/dockerfile

ARG WORKDIR="/workspaces/my_music_tool-kt/"

FROM docker.io/library/ubuntu:24.04 as base

ARG WORKDIR
WORKDIR ${WORKDIR}

ARG DEBIAN_FRONTEND="noninteractive"

# @see: https://github.com/moby/buildkit/blob/master/frontend/dockerfile/docs/reference.md#run---mounttypecache
RUN <<EOF
    rm -rf "/etc/apt/apt.conf.d/docker-clean"
EOF

# hadolint ignore=DL3008
RUN --mount=type=cache,target=/var/cache/apt/,sharing=locked --mount=type=cache,target=/var/lib/apt/,sharing=locked <<EOF
    apt-get update
    apt-get -y install --no-install-recommends \
        "git" \
        "neovim" \
        ;
EOF

FROM base as deps

ARG WORKDIR
WORKDIR ${WORKDIR}

ENV ANDROID_HOME="/usr/lib/android-sdk/"
ENV ANDROID_NDK_HOME="/usr/lib/android-ndk/"
ENV ANDROID_USER_HOME="/root/.android/"

# hadolint ignore=DL3008
RUN --mount=type=cache,target=/var/cache/apt/,sharing=locked --mount=type=cache,target=/var/lib/apt/,sharing=locked <<EOF
    apt-get update
    apt-get -y install --no-install-recommends \
        "google-android-build-tools-34.0.0-installer" \
        "google-android-cmdline-tools-12.0-installer" \
        "google-android-emulator-installer" \
        "google-android-licenses" \
        "google-android-ndk-r26-installer" \
        "google-android-platform-34-installer" \
        "google-android-platform-tools-installer" \
        "google-android-sources-34-installer" \
        "openjdk-21-jdk" \
        ;
EOF

# TODO: install "system-images;android-34;google_apis;arm64-v8a" "system-images;android-34;google_apis;x86_64"

CMD ["sleep", "infinity"]

FROM deps as builder

ARG WORKDIR
WORKDIR ${WORKDIR}

# hadolint global ignore=DL3022
COPY --from=rs "./target/aarch64-linux-android/release/libspotify.so" "${WORKDIR}/src/composeApp/src/androidMain/jniLibs/arm64-v8a/"
COPY --from=rs "./target/x86_64-linux-android/release/libspotify.so"  "${WORKDIR}/src/composeApp/src/androidMain/jniLibs/x86_64/"
COPY --from=rs "./out/uniffi/"                                        "${WORKDIR}/src/composeApp/src/commonMain/kotlin/uniffi/"

# TODO: Gradle v9.0 ft. Kotlin 2.0
COPY "./.m2/" "${WORKDIR}/.m2/"

COPY "./gradle/"             "${WORKDIR}/gradle/"
COPY "./src/"                "${WORKDIR}/src/"
COPY "./build.gradle.kts"    "${WORKDIR}/"
COPY "./gradle.properties"   "${WORKDIR}/"
COPY "./gradlew"             "${WORKDIR}/"
COPY "./settings.gradle.kts" "${WORKDIR}/"

RUN --mount=type=cache,target=/root/.gradle/,sharing=locked --mount=type=cache,target=/root/.kotlin/,sharing=locked --mount=type=bind,source=./jks/,target=${WORKDIR}/jks/,readonly=true <<EOF
    ./gradlew assembleRelease
EOF

# hadolint ignore=DL3006
FROM scratch as artifact

ARG WORKDIR

COPY --from=builder "${WORKDIR}/out/src/composeApp/build/outputs/apk/release/composeApp-release.apk" "/out/src/composeApp/build/outputs/apk/release/"

FROM docker.io/library/eclipse-temurin:21-jdk-alpine as runner

ARG WORKDIR
WORKDIR ${WORKDIR}
