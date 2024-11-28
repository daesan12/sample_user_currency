-- User 테이블 생성
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(10) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

-- Currency 테이블 생성
CREATE TABLE currency (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          currency_name VARCHAR(3) NOT NULL,
                          exchange_rate DECIMAL(19, 4) NOT NULL CHECK (exchange_rate > 0),
                          symbol CHAR(1) NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

-- Exchange 테이블 생성
CREATE TABLE exchange (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          to_user_id BIGINT NOT NULL,
                          to_currency_id BIGINT NOT NULL,
                          amount_in_krw DECIMAL(19, 4) NOT NULL CHECK (amount_in_krw >= 1),
                          amount_after_exchange DECIMAL(19, 4) NOT NULL,
                          status VARCHAR(255) DEFAULT 'normal',
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
                          CONSTRAINT fk_exchange_user FOREIGN KEY (to_user_id) REFERENCES user (id) ON DELETE CASCADE,
                          CONSTRAINT fk_exchange_currency FOREIGN KEY (to_currency_id) REFERENCES currency (id) ON DELETE CASCADE
);