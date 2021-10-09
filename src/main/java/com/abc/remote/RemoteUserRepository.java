package com.abc.remote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RemoteUserRepository extends JpaRepository<RemoteUser, String> {
}
