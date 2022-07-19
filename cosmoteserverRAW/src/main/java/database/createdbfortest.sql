 SET @tableName = 'kostas';
 SET @q = CONCAT('
        CREATE TABLE IF NOT EXISTS `' , @tableName, '` (
            `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
            `something` VARCHAR(10) NOT NULL,
            `somedate` DATETIME NOT NULL,
            PRIMARY KEY (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8
    ');
    PREPARE stmt FROM @q;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;