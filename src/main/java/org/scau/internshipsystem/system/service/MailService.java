package org.scau.internshipsystem.system.service;

import org.scau.internshipsystem.system.entity.Mail;

public interface MailService {
    boolean sendPlainMessage(Mail mail);
}
