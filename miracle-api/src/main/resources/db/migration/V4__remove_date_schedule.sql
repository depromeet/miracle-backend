ALTER TABLE `schedule` DROP COLUMN `year`;
ALTER TABLE `schedule` DROP COLUMN `month`;
ALTER TABLE `schedule` DROP COLUMN `day`;
ALTER TABLE `schedule` DROP COLUMN `day_of_week`;
ALTER TABLE `schedule` DROP COLUMN `loop_type`;
ALTER TABLE `schedule` ADD COLUMN `day_of_the_week` varchar(255) null;
